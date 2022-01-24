package com.flexbank.ws.controller;

import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.dto.LoanRequestNotificationDto;
import com.flexbank.ws.service.inter.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured("ROLE_ADMIN")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

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
    public ResponseEntity<?> approveLoanRequest(@RequestParam(value = "loanRequestId")
                                                            Integer loanRequestId){

        adminService.approveLoanRequest(loanRequestId);

        return ResponseEntity.ok("Loan request is approved successfully!");
    }
}
