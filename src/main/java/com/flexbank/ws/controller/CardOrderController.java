package com.flexbank.ws.controller;

import com.flexbank.ws.dto.request.CardOrderRequest;
import com.flexbank.ws.service.inter.CardOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Secured("ROLE_CUSTOMER")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cardOrders")
public class CardOrderController {

    private final CardOrderService cardOrderService;

    @PostMapping("/order")
    public ResponseEntity<?> orderCard(@RequestBody CardOrderRequest cardOrderRequest) throws Exception {

        cardOrderService.orderCard(cardOrderRequest);

        return ResponseEntity.ok("Operation has been successfully completed!");
    }
}