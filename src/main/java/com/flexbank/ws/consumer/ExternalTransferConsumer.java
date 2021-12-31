package com.flexbank.ws.consumer;

import com.flexbank.ws.client.ibanapi.IbanApiClient;
import com.flexbank.ws.client.ibanapi.IbanApiModel;
import com.flexbank.ws.configuration.rabbitmq.RabbitMqConfiguration;
import com.flexbank.ws.dto.request.ExternalTransferRequest;
import com.flexbank.ws.entity.Card;
import com.flexbank.ws.exception.BadRequestException;
import com.flexbank.ws.exception.ErrorMessage;
import com.flexbank.ws.exception.NotFoundException;
import com.flexbank.ws.repository.CardRepository;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalTransferConsumer {

    private final IbanApiClient ibanApiClient;
    private final CardRepository cardRepository;
    private final DirectExchange exchange;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqConfiguration rabbitMqConfiguration;

    @Autowired
    public ExternalTransferConsumer(IbanApiClient ibanApiClient,
                                    CardRepository cardRepository,
                                    DirectExchange exchange,
                                    RabbitTemplate rabbitTemplate,
                                    RabbitMqConfiguration rabbitMqConfiguration) {
        this.ibanApiClient = ibanApiClient;
        this.cardRepository = cardRepository;
        this.exchange = exchange;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMqConfiguration = rabbitMqConfiguration;
    }

    @RabbitListener(queues = "${rabbitmq.external_transfer_queue}")
    public void validateExternalTransferMessage(ExternalTransferRequest externalTransferRequest) throws Exception {

        IbanApiModel ibanApiModel =
                ibanApiClient.validateIban(externalTransferRequest.getIban());

        if(ibanApiModel.getResult().equals("400")){
            throw new BadRequestException(ErrorMessage.INVALID_IBAN.getErrorMessage());
        }

        if(!externalTransferRequest.getSwiftCode().equals(ibanApiModel.getSwiftCode())){
            throw new BadRequestException(ErrorMessage.INVALID_SWIFT.getErrorMessage());
        }

        Card senderCard = cardRepository
                .findByCardNumber(externalTransferRequest.getChosenCard());

        Double amount = externalTransferRequest.getAmount();

        if(senderCard.getBalance() < amount){
            throw new NotFoundException(ErrorMessage.INSUFFICIENT_BALANCE.getErrorMessage());
        }

        rabbitTemplate.convertAndSend(exchange.getName(),
                rabbitMqConfiguration.getApprovedExternalTransferRoutingKey(),
                externalTransferRequest);
    }
}
