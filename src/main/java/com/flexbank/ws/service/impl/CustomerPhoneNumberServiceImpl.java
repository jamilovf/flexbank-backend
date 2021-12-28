package com.flexbank.ws.service.impl;

import com.flexbank.ws.converter.CustomerPhoneNumberConverter;
import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.dto.request.SmsCodeRequest;
import com.flexbank.ws.entity.CustomerPhoneNumber;
import com.flexbank.ws.repository.CustomerPhoneNumberRepository;
import com.flexbank.ws.service.inter.CustomerPhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerPhoneNumberServiceImpl implements CustomerPhoneNumberService {

    private final CustomerPhoneNumberRepository customerPhoneNumberRepository;

    private final CustomerPhoneNumberConverter customerPhoneNumberConverter;

    @Autowired
    public CustomerPhoneNumberServiceImpl(
            CustomerPhoneNumberRepository customerPhoneNumberRepository,
            CustomerPhoneNumberConverter customerPhoneNumberConverter) {

        this.customerPhoneNumberRepository = customerPhoneNumberRepository;
        this.customerPhoneNumberConverter = customerPhoneNumberConverter;
    }

    @Override
    public CustomerPhoneNumberDto findByPhoneNumber(String phoneNumber) {

       CustomerPhoneNumber customerPhoneNumber =
               customerPhoneNumberRepository.findByPhoneNumber(phoneNumber);

       if(customerPhoneNumber == null){
           throw new RuntimeException("No customer with this phone number!");
       }

       if(customerPhoneNumber.isRegistered()){
            throw new RuntimeException("Customer is already registered!");
       }

       CustomerPhoneNumberDto customerPhoneNumberDto =
               customerPhoneNumberConverter.entityToDto(customerPhoneNumber);

       return customerPhoneNumberDto;
    }

    @Override
    public CustomerPhoneNumberDto verifySmsCode(String phoneNumber, String smsCode) {

        CustomerPhoneNumber customerPhoneNumber =
                customerPhoneNumberRepository.findByPhoneNumber(phoneNumber);

        if(!customerPhoneNumber.getMessageCode().equals(smsCode)){
            throw new RuntimeException("Wrong message code!");
        }

        customerPhoneNumber.setSignupAllowed(true);
        customerPhoneNumberRepository.save(customerPhoneNumber);

        CustomerPhoneNumberDto customerPhoneNumberDto =
                customerPhoneNumberConverter.entityToDto(customerPhoneNumber);

        return customerPhoneNumberDto;
    }
}
