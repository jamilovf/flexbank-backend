package com.flexbank.ws.service.impl;

import com.flexbank.ws.configuration.rabbitmq.RabbitMqConfiguration;
import com.flexbank.ws.dto.CardDto;
import com.flexbank.ws.dto.request.CardOrderRequest;
import com.flexbank.ws.entity.CardOrder;
import com.flexbank.ws.exception.BadRequestException;
import com.flexbank.ws.exception.ErrorMessage;
import com.flexbank.ws.repository.CardOrderRepository;
import com.flexbank.ws.service.inter.CardOrderService;
import com.flexbank.ws.service.inter.CardService;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CardOrderServiceImpl implements CardOrderService {

    private final DirectExchange exchange;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqConfiguration rabbitMqConfiguration;
    private final CardService cardService;
    private final CardOrderRepository cardOrderRepository;

    @Autowired
    public CardOrderServiceImpl(DirectExchange exchange,
                                RabbitTemplate rabbitTemplate,
                                RabbitMqConfiguration rabbitMqConfiguration,
                                CardService cardService,
                                CardOrderRepository cardOrderRepository) {
        this.exchange = exchange;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMqConfiguration = rabbitMqConfiguration;
        this.cardService = cardService;
        this.cardOrderRepository = cardOrderRepository;
    }

    @Override
    public void orderCard(CardOrderRequest cardOrderRequest) throws Exception{
        List<CardDto> cardDtoList =
                cardService.findAllByCustomerId(cardOrderRequest.getCustomerId());

        if(!cardDtoList.isEmpty()){
        for (CardDto cardDto : cardDtoList ) {
            if(cardDto.getCardType().equals(cardOrderRequest.getType())){
                throw new BadRequestException(ErrorMessage.CARD_ALREADY_EXISTS.getErrorMessage());
            }
          }
        }

        rabbitTemplate.convertAndSend(exchange.getName(),
                rabbitMqConfiguration.getOrderCardRoutingKey(),
                cardOrderRequest);
    }

    @Override
    public void addCardOrder(CardOrderRequest cardOrderRequest) {
        CardOrder cardOrder = CardOrder.builder()
                .type(cardOrderRequest.getType())
                .createdAt(LocalDate.now())
                .customerId(cardOrderRequest.getCustomerId())
                .build();

        cardOrderRepository.save(cardOrder);
    }
}
