package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.request.CardOrderRequest;

public interface CardOrderService {
    void orderCard(CardOrderRequest cardOrderRequest) throws Exception;

    void addCardOrder(CardOrderRequest cardOrderRequest);
}
