package com.flexbank.ws.service.impl;

import com.flexbank.ws.configuration.security.SecurityContextService;
import com.flexbank.ws.converter.CustomerConverter;
import com.flexbank.ws.dto.CustomerDetailsDto;
import com.flexbank.ws.dto.request.UpdateCustomerRequest;
import com.flexbank.ws.entity.Customer;
import com.flexbank.ws.exception.BadRequestException;
import com.flexbank.ws.exception.ErrorMessage;
import com.flexbank.ws.repository.CustomerRepository;
import com.flexbank.ws.service.inter.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;

    @Override
    public void findByEmail(String email) throws Exception {

        Customer customer = customerRepository.findByEmail(email);
        if (customer != null){
            throw new BadRequestException(ErrorMessage.EMAIL_ALREADY_REGISTERED.getErrorMessage());
        }

    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void updateAccount(UpdateCustomerRequest updateCustomerRequest) {

        Integer customerId = SecurityContextService.getCurrentCustomerId();
        Customer customer = customerRepository.findById(customerId).get();

        customer.setAddress(updateCustomerRequest.getAddress());
        customer.setCity(updateCustomerRequest.getCity());
        customer.setPhoneNumber(updateCustomerRequest.getPhoneNumber());
        customer.setZip(updateCustomerRequest.getZip());

        customerRepository.save(customer);
    }

    @Override
    public CustomerDetailsDto getAccountDetails() {

        Integer customerId = SecurityContextService.getCurrentCustomerId();
        Customer customer = customerRepository.findById(customerId).get();

        CustomerDetailsDto customerDetailsDto = customerConverter.entityToDetailsDto(customer);

        return customerDetailsDto;
    }
}
