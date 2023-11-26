package com.flexbank.ws.controller;

import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.dto.LoanRequestNotificationDto;
import com.flexbank.ws.dto.request.LoanResultRequest;
import com.flexbank.ws.service.inter.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured("ROLE_ADMIN")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/registerCustomerDetails")
    public ResponseEntity<?> registerCustomerDetails(
            @RequestBody CustomerPhoneNumberDto customerPhoneNumberDto){

        adminService.registerCustomerDetails(customerPhoneNumberDto);

        return ResponseEntity.ok("Customer details are registered successfully!");
    }

    @GetMapping("/getAllLoanRequestNotifications")
    public ResponseEntity<?> getAllLoanRequestNotifications(){

       List<LoanRequestNotificationDto> loanRequestNotificationDtos =
               adminService.getAllLoanRequestNotifications();

        return ResponseEntity.ok(loanRequestNotificationDtos);
    }

    @PutMapping("/approveLoanRequest")
    public ResponseEntity<?> approveLoanRequest(@RequestBody LoanResultRequest loanResultRequest){

        adminService.approveLoanRequest(loanResultRequest.getLoanRequestId());

        return ResponseEntity.ok("Loan request is approved successfully!");
    }

    @PutMapping("/declineLoanRequest")
    public ResponseEntity<?> declineLoanRequest(@RequestBody LoanResultRequest loanResultRequest){

        adminService.declineLoanRequest(loanResultRequest.getLoanRequestId());

        return ResponseEntity.ok("Loan request is declined successfully!");
    }
}
