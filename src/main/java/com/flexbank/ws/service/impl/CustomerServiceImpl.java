package com.flexbank.ws.service.impl;

import com.flexbank.ws.converter.CustomerConverter;
import com.flexbank.ws.entity.Customer;
import com.flexbank.ws.repository.CustomerRepository;
import com.flexbank.ws.service.inter.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;


    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerConverter customerConverter) {
        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;

    }

    @Override
    public void findByEmail(String email) {

        Customer customer = customerRepository.findByEmail(email);
        if (customer != null){
            throw new RuntimeException("Email already is registered!");
        }

    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }
}
