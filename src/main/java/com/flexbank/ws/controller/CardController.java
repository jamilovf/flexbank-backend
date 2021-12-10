package com.flexbank.ws.controller;

import com.flexbank.ws.dto.CardDto;
import com.flexbank.ws.service.inter.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllByCustomerId(@RequestParam Integer customerId){

        List<CardDto> cardDtos =
                cardService.findAllByCustomerId(customerId);

        return ResponseEntity.ok(cardDtos);
    }

}
