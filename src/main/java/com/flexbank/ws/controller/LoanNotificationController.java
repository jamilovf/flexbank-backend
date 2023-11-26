package com.flexbank.ws.controller;

import com.flexbank.ws.dto.LoanNotificationDto;
import com.flexbank.ws.service.inter.LoanNotificationService;
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
@RequestMapping("/api/loanNotifications")
public class LoanNotificationController {

    private final LoanNotificationService loanNotificationService;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllByCustomerId(){

        List<LoanNotificationDto> loanNotificationDtos = loanNotificationService.findAllByCustomerId();

        return ResponseEntity.ok(loanNotificationDtos);
    }
}
