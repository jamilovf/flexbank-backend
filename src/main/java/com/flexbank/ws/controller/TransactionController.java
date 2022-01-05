package com.flexbank.ws.controller;

import com.flexbank.ws.dto.TransactionDto;
import com.flexbank.ws.dto.request.ExternalTransferRequest;
import com.flexbank.ws.dto.request.InternalTransferRequest;
import com.flexbank.ws.dto.request.SearchRequest;
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
    public ResponseEntity<?> findAllByCustomerId(Authentication authentication,
                                                 @RequestParam(value = "page") int page,
                                                 @RequestParam(value = "limit",
                                                         defaultValue = "10") int limit){

        Integer customerId = Integer.parseInt(authentication.getPrincipal().toString());

        List<TransactionDto> transactionDtos =
                transactionService.findAllByCustomerId(customerId, page, limit);

        return ResponseEntity.ok(transactionDtos);
    }

    @PostMapping("/transferInternal")
    public ResponseEntity<?> transferInternal(
            @RequestBody InternalTransferRequest internalTransferRequest) throws Exception {

        transactionService.transferInternal(internalTransferRequest);

        return ResponseEntity.ok("Successful transfer!");
    }

    @PostMapping("/transferExternal")
    public ResponseEntity<?> transferInternal(
            @RequestBody ExternalTransferRequest externalTransferRequest){

        transactionService.transferExternal(externalTransferRequest);

        return ResponseEntity.ok("Operation has been successfully completed!");
    }

    @GetMapping("/countPages")
    public ResponseEntity<?> findAllByCustomerId(Authentication authentication){

        Integer customerId = Integer.parseInt(authentication.getPrincipal().toString());

        Integer count = transactionService.countPagesByCustomerId(customerId);

        return ResponseEntity.ok(count);
    }

    @GetMapping("/search")
    public ResponseEntity<?> filterTransactions(Authentication authentication,
                                                @RequestBody SearchRequest searchRequest,
                                                @RequestParam(value = "page") int page,
                                                @RequestParam(value = "limit",
                                                        defaultValue = "10") int limit){

        Integer customerId = Integer.parseInt(authentication.getPrincipal().toString());

        if(searchRequest.getType2() == null){
            searchRequest.setType2(searchRequest.getType1());
        }

        List<TransactionDto> transactionDtos =
                transactionService
                        .searchTransactionsByDateAndType(customerId,
                                searchRequest.getFrom(), searchRequest.getTo(),
                                searchRequest.getType1(), searchRequest.getType2(),
                                page, limit);

        return ResponseEntity.ok(transactionDtos);
    }
}
