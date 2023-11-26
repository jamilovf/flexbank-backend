package com.flexbank.ws.controller;

import com.flexbank.ws.dto.CustomerDetailsDto;
import com.flexbank.ws.dto.request.UpdateCustomerRequest;
import com.flexbank.ws.service.inter.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Secured("ROLE_CUSTOMER")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody UpdateCustomerRequest updateCustomerRequest){

        customerService.updateAccount(updateCustomerRequest);

        return ResponseEntity.ok("Customer is updated successfully!");
    }

    @GetMapping("/getDetails")
    public ResponseEntity<?> updateAccount(){

        CustomerDetailsDto customerDetailsDto =
                customerService.getAccountDetails();

        return ResponseEntity.ok(customerDetailsDto);
    }
}
