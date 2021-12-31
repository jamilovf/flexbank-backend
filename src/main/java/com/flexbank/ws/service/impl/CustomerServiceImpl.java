package com.flexbank.ws.service.impl;

import com.flexbank.ws.converter.CustomerConverter;
import com.flexbank.ws.dto.request.UpdateCustomerRequest;
import com.flexbank.ws.entity.Customer;
import com.flexbank.ws.exception.BadRequestException;
import com.flexbank.ws.exception.ErrorMessage;
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
    public void updateAccount(Integer customerId, UpdateCustomerRequest updateCustomerRequest) {

        Customer customer = customerRepository.findById(customerId).get();

        customer.setFirstName(updateCustomerRequest.getFirstName());
        customer.setLastName(updateCustomerRequest.getLastName());
        customer.setAddress(updateCustomerRequest.getAddress());
        customer.setBirthDate(updateCustomerRequest.getBirthDate());
        customer.setCity(updateCustomerRequest.getCity());
        customer.setPhoneNumber(updateCustomerRequest.getPhoneNumber());
        customer.setZip(updateCustomerRequest.getZip());

        customerRepository.save(customer);
    }
}
