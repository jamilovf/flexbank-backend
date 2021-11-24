package com.flexbank.ws.controller;

import com.flexbank.ws.dto.TransactionDto;
import com.flexbank.ws.service.inter.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllByCustomerId(@RequestParam Integer customerId){

        List<TransactionDto> transactionDtos =
                transactionService.findAllByCustomerId(customerId);

        return ResponseEntity.ok(transactionDtos);
    }
}
