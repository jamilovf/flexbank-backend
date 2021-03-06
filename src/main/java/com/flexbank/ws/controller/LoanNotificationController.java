package com.flexbank.ws.controller;

import com.flexbank.ws.dto.LoanNotificationDto;
import com.flexbank.ws.service.inter.LoanNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured("ROLE_CUSTOMER")
@RestController
@RequestMapping("/api/loanNotifications")
public class LoanNotificationController {

    private final LoanNotificationService loanNotificationService;

    @Autowired
    public LoanNotificationController(LoanNotificationService loanNotificationService) {
        this.loanNotificationService = loanNotificationService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllByCustomerId(Authentication authentication){

        Integer customerId = Integer.parseInt(authentication.getPrincipal().toString());

        List<LoanNotificationDto> loanNotificationDtos =
                loanNotificationService.findAllByCustomerId(customerId);

        return ResponseEntity.ok(loanNotificationDtos);
    }
}
