package com.flexbank.ws.controller;

import com.flexbank.ws.dto.request.UpdateCustomerRequest;
import com.flexbank.ws.service.inter.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateAccount(Authentication authentication,
                                           @RequestBody UpdateCustomerRequest updateCustomerRequest){

        Integer customerId = Integer.parseInt(authentication.getPrincipal().toString());

        customerService.updateAccount(customerId, updateCustomerRequest);

        return ResponseEntity.ok("Customer is updated successfully!");
    }
}
