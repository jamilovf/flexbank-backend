package com.flexbank.ws.controller;

import com.flexbank.ws.dto.TransactionDto;
import com.flexbank.ws.dto.request.ExternalTransferRequest;
import com.flexbank.ws.dto.request.InternalTransferRequest;
import com.flexbank.ws.dto.request.LoanPaymentRequest;
import com.flexbank.ws.service.inter.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured("ROLE_CUSTOMER")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllByCustomerId(@RequestParam(value = "page") int page,
                                                 @RequestParam(value = "limit",
                                                         defaultValue = "10") int limit){
        List<TransactionDto> transactionDtos =
                transactionService.findAllByCustomerId(page, limit);

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
    public ResponseEntity<?> getCountPages(){

        Integer count = transactionService.countPagesByCustomerId();

        return ResponseEntity.ok(count);
    }

    @GetMapping("/search")
    public ResponseEntity<?> filterTransactions(@RequestParam(value = "from") String from,
                                                @RequestParam(value = "to") String to,
                                                @RequestParam(value = "type1") String type1,
                                                @RequestParam(value = "type2") String type2,
                                                @RequestParam(value = "page") int page,
                                                @RequestParam(value = "limit",
                                                        defaultValue = "3") int limit){

        if(type2.isEmpty()){
            type2 = type1;
        }

        if(type1.isEmpty()){
            type1 = type2;
        }

        List<TransactionDto> transactionDtos =
                transactionService
                        .searchTransactionsByDateAndType(from, to, type1, type2, page, limit);

        return ResponseEntity.ok(transactionDtos);
    }

    @PutMapping("/payLoan")
    public ResponseEntity<?> payLoan(@RequestBody LoanPaymentRequest loanPaymentRequest) throws Exception {

        transactionService.payLoan(loanPaymentRequest.getLoanId(), loanPaymentRequest.getCardId());

        return ResponseEntity.ok("Loan is paid succesfully!");
    }
}
