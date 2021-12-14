package com.flexbank.ws.service.impl;

import com.flexbank.ws.converter.CustomerConverter;
import com.flexbank.ws.dto.CustomerDto;
import com.flexbank.ws.entity.Customer;
import com.flexbank.ws.repository.CustomerRepository;
import com.flexbank.ws.service.inter.CustomerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;


    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerConverter customerConverter,
                               BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerRepository = customerRepository;
        this.customerConverter = customerConverter;

    }

    @Override
    public CustomerDto findByEmail(String email) {

        Customer customer = customerRepository.findByEmail(email);
        if (customer != null){
            throw new RuntimeException("Email already is registered!");
        }
        CustomerDto customerDto = customerConverter.entityToDto(customer);

        return customerDto;
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }
}