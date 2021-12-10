package com.flexbank.ws.controller;

import com.flexbank.ws.dto.LoanNotificationDto;
import com.flexbank.ws.service.inter.LoanNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loanNotifications")
public class LoanNotificationController {

    private final LoanNotificationService loanNotificationService;

    @Autowired
    public LoanNotificationController(LoanNotificationService loanNotificationService) {
        this.loanNotificationService = loanNotificationService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllByCustomerId(@RequestParam Integer customerId){

        List<LoanNotificationDto> loanNotificationDtos =
                loanNotificationService.findAllByCustomerId(customerId);

        return ResponseEntity.ok(loanNotificationDtos);
    }
}
