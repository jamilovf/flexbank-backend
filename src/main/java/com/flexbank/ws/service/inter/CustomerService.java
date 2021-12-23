package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.CustomerDto;
import com.flexbank.ws.dto.request.UpdateCustomerRequest;
import com.flexbank.ws.entity.Customer;

public interface CustomerService {

    void findByEmail(String email);

    void save(Customer customer);

    void updateAccount(Integer customerId, UpdateCustomerRequest updateCustomerRequest);
}
