package com.flexbank.ws.controller;

import com.flexbank.ws.dto.LoanRequestDto;
import com.flexbank.ws.entity.LoanRequest;
import com.flexbank.ws.service.inter.LoanRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loanRequests")
public class LoanRequestController {

    private final LoanRequestService loanRequestService;

    @Autowired
    public LoanRequestController(LoanRequestService loanRequestService) {
        this.loanRequestService = loanRequestService;
    }

    @PostMapping("/requestPersonalLoan")
    public ResponseEntity<?> requestPersonalLoan(Authentication authentication,
                                                 @RequestBody LoanRequestDto loanRequestDto){

        Integer customerId = Integer.parseInt(authentication.getPrincipal().toString());

        loanRequestService.requestPersonalLoan(customerId, loanRequestDto);

        return ResponseEntity.ok("Your request has been considered!");
    }

    @PostMapping("/requestCarLoan")
    public ResponseEntity<?> requestCarLoan(Authentication authentication,
                                                 @RequestBody LoanRequestDto loanRequestDto){

        Integer customerId = Integer.parseInt(authentication.getPrincipal().toString());

        loanRequestService.requestCarLoan(customerId, loanRequestDto);

        return ResponseEntity.ok("Your request has been considered!");
    }
}
