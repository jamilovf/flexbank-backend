package com.flexbank.ws.consumer;

import com.flexbank.ws.dto.request.CardOrderRequest;
import com.flexbank.ws.service.inter.CardOrderService;
import com.flexbank.ws.service.inter.CardService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderCardConsumer {

    private final CardOrderService cardOrderService;
    private final CardService cardService;

    public OrderCardConsumer(CardOrderService cardOrderService, CardService cardService) {
        this.cardOrderService = cardOrderService;
        this.cardService = cardService;
    }

    @RabbitListener(queues = "${rabbitmq.order_card_queue}")
    public void validateOrderCardMessage(CardOrderRequest cardOrderRequest){
            cardOrderService.addCardOrder(cardOrderRequest);
            cardService.createCard(cardOrderRequest);
    }
}
