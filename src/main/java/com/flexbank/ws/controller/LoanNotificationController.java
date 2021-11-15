package com.flexbank.ws.controller;

import com.flexbank.ws.converter.LoanNotificationConverter;
import com.flexbank.ws.dto.LoanNotificationDto;
import com.flexbank.ws.entity.LoanNotification;
import com.flexbank.ws.service.LoanNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loanNotifications")
@RequiredArgsConstructor
public class LoanNotificationController {

    private final LoanNotificationService loanNotificationService;

    private final LoanNotificationConverter loanNotificationConverter;

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllByCustomerId(@RequestParam Integer customerId){

        List<LoanNotification> loanNotifications = loanNotificationService.findAllByCustomerId(customerId);
       List<LoanNotificationDto> loanNotificationDtos =
               loanNotifications.stream()
                .map(loanNotification -> loanNotificationConverter.entityToDto(loanNotification))
                .collect(Collectors.toList());

        return ResponseEntity.ok(loanNotificationDtos);
    }
}
