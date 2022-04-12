package com.flexbank.ws.controller;

import com.flexbank.ws.dto.CardDto;
import com.flexbank.ws.dto.request.BlockCardRequest;
import com.flexbank.ws.service.inter.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured("ROLE_CUSTOMER")
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

    @PutMapping("/block")
    public ResponseEntity<?> blockCard(
            @RequestBody BlockCardRequest blockCardRequest) throws Exception {

        CardDto cardDto = cardService.blockCard(blockCardRequest.getId());

        return ResponseEntity.ok(cardDto);
    }

    @PutMapping("/unblock")
    public ResponseEntity<?> unblockCard(
            @RequestBody BlockCardRequest blockCardRequest) throws Exception {

        CardDto cardDto = cardService.unblockCard(blockCardRequest.getId());

        return ResponseEntity.ok(cardDto);
    }
}
