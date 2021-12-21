package com.flexbank.ws.controller;

import com.flexbank.ws.dto.TransactionDto;
import com.flexbank.ws.dto.request.ExternalTransferRequest;
import com.flexbank.ws.dto.request.InternalTransferRequest;
import com.flexbank.ws.service.inter.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllByCustomerId(@RequestParam Integer customerId){

        List<TransactionDto> transactionDtos =
                transactionService.findAllByCustomerId(customerId);

        return ResponseEntity.ok(transactionDtos);
    }

    @PostMapping("/transferInternal")
    public ResponseEntity<?> transferInternal(
            @RequestBody InternalTransferRequest internalTransferRequest){

        transactionService.transferInternal(internalTransferRequest);

        return ResponseEntity.ok("Successful transfer!");
    }

    @PostMapping("/transferExternal")
    public ResponseEntity<?> transferInternal(
            @RequestBody ExternalTransferRequest externalTransferRequest){

        transactionService.transferExternal(externalTransferRequest);

        return ResponseEntity.ok("Operation has been successfully completed!");
    }

}
