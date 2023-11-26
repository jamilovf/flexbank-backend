package com.flexbank.ws.consumer;

import com.flexbank.ws.client.ibanapi.IbanApiClient;
import com.flexbank.ws.client.ibanapi.IbanApiModel;
import com.flexbank.ws.configuration.rabbitmq.RabbitMqConfiguration;
import com.flexbank.ws.dto.request.DeclinedExternalTransferRequest;
import com.flexbank.ws.dto.request.ExternalTransferRequest;
import com.flexbank.ws.entity.Card;
import com.flexbank.ws.exception.ErrorMessage;
import com.flexbank.ws.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExternalTransferConsumer {

    private final IbanApiClient ibanApiClient;
    private final CardRepository cardRepository;
    private final DirectExchange exchange;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqConfiguration rabbitMqConfiguration;

    @RabbitListener(queues = "${rabbitmq.external_transfer_queue}")
    public void validateExternalTransferMessage(ExternalTransferRequest externalTransferRequest) throws Exception {

        IbanApiModel ibanApiModel =
                ibanApiClient.validateIban(externalTransferRequest.getIban());

        if(ibanApiModel.getResult().contains("400")){
            rabbitTemplate.convertAndSend(exchange.getName(),
                    rabbitMqConfiguration.getDeclinedExternalTransferRoutingKey(),
                    new DeclinedExternalTransferRequest(externalTransferRequest,
                            ErrorMessage.INVALID_IBAN.getErrorMessage())
                    );
            return;
        }

        if(!externalTransferRequest.getSwiftCode().equals(ibanApiModel.getSwiftCode())){
            rabbitTemplate.convertAndSend(exchange.getName(),
                    rabbitMqConfiguration.getDeclinedExternalTransferRoutingKey(),
                    new DeclinedExternalTransferRequest(externalTransferRequest,
                            ErrorMessage.INVALID_SWIFT.getErrorMessage()));
            return;
        }

        Card senderCard = cardRepository
                .findById(externalTransferRequest.getChosenCardId()).get();

        Double amount = externalTransferRequest.getAmount();

        if(senderCard.getBalance() < amount){
            rabbitTemplate.convertAndSend(exchange.getName(),
                    rabbitMqConfiguration.getDeclinedExternalTransferRoutingKey(),
                    new DeclinedExternalTransferRequest(externalTransferRequest,
                            ErrorMessage.INSUFFICIENT_BALANCE.getErrorMessage()));
            return;
        }

        rabbitTemplate.convertAndSend(exchange.getName(),
                rabbitMqConfiguration.getApprovedExternalTransferRoutingKey(),
                externalTransferRequest);
    }
}
