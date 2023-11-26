package com.flexbank.ws.controller;

import com.flexbank.ws.dto.LoanRequestDto;
import com.flexbank.ws.entity.LoanRequest;
import com.flexbank.ws.service.inter.LoanRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Secured("ROLE_CUSTOMER")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loanRequests")
public class LoanRequestController {

    private final LoanRequestService loanRequestService;

    @PostMapping("/requestPersonalLoan")
    public ResponseEntity<?> requestPersonalLoan(@RequestBody LoanRequestDto loanRequestDto){

        loanRequestService.requestPersonalLoan(loanRequestDto);

        return ResponseEntity.ok("Your request has been considered!");
    }

    @PostMapping("/requestCarLoan")
    public ResponseEntity<?> requestCarLoan(@RequestBody LoanRequestDto loanRequestDto){

        loanRequestService.requestCarLoan(loanRequestDto);

        return ResponseEntity.ok("Your request has been considered!");
    }
}
