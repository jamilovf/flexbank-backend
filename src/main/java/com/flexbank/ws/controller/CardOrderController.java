package com.flexbank.ws.controller;

import com.flexbank.ws.dto.request.CardOrderRequest;
import com.flexbank.ws.service.inter.CardOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cardOrders")
public class CardOrderController {

    private final CardOrderService cardOrderService;

    @Autowired
    public CardOrderController(CardOrderService cardOrderService) {
        this.cardOrderService = cardOrderService;
    }

    @PostMapping("/order")
    public ResponseEntity<?> orderCard(Authentication authentication,
                                                 @RequestBody CardOrderRequest cardOrderRequest) throws Exception {

        Integer customerId = Integer.parseInt(authentication.getPrincipal().toString());
        cardOrderRequest.setCustomerId(customerId);

        cardOrderService.orderCard(cardOrderRequest);

        return ResponseEntity.ok("Operation has been successfully completed!");
    }
}