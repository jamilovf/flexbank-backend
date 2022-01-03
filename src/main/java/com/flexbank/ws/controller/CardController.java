package com.flexbank.ws.controller;

import com.flexbank.ws.dto.CardDto;
import com.flexbank.ws.service.inter.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> findAllByCustomerId(Authentication authentication){

        Integer customerId = Integer.parseInt(authentication.getPrincipal().toString());

        List<CardDto> cardDtos =
                cardService.findAllByCustomerId(customerId);

        return ResponseEntity.ok(cardDtos);
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<?> blockCard(@PathVariable Integer id) throws Exception {

        cardService.blockCard(id);

        return ResponseEntity.ok("Card is blocked!");
    }
}
