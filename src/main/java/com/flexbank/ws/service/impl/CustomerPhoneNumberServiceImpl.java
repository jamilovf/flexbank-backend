package com.flexbank.ws.service.impl;

import com.flexbank.ws.converter.CustomerPhoneNumberConverter;
import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.dto.request.SmsCodeRequest;
import com.flexbank.ws.entity.CustomerPhoneNumber;
import com.flexbank.ws.exception.BadRequestException;
import com.flexbank.ws.exception.ErrorMessage;
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
    public CustomerPhoneNumber findByPhoneNumber(String phoneNumber) throws Exception {

       CustomerPhoneNumber customerPhoneNumber =
               customerPhoneNumberRepository.findByPhoneNumber(phoneNumber);

       if(customerPhoneNumber == null){
           throw new BadRequestException(ErrorMessage.WRONG_PHONE_NUMBER.getErrorMessage());
       }

       return customerPhoneNumber;
    }

    @Override
    public CustomerPhoneNumberDto verifySmsCode(String phoneNumber, String smsCode) throws Exception {

        CustomerPhoneNumber customerPhoneNumber =
                customerPhoneNumberRepository.findByPhoneNumber(phoneNumber);

        if(customerPhoneNumber.getMessageCodeAttempt() > 3){
            throw new BadRequestException(ErrorMessage.ATTEMPT_NOT_ALLOWED.getErrorMessage());
        }

        if(!customerPhoneNumber.getMessageCode().equals(smsCode)){
            customerPhoneNumber.setMessageCodeAttempt(customerPhoneNumber.getMessageCodeAttempt() + 1);
            customerPhoneNumberRepository.save(customerPhoneNumber);
            throw new BadRequestException(ErrorMessage.WRONG_MESSAGE_CODE.getErrorMessage());
        }

        customerPhoneNumber.setSignupAllowed(true);
        customerPhoneNumberRepository.save(customerPhoneNumber);

        CustomerPhoneNumberDto customerPhoneNumberDto =
                customerPhoneNumberConverter.entityToDto(customerPhoneNumber);

        return customerPhoneNumberDto;
    }

    @Override
    public CustomerPhoneNumberDto verifySmsCodeForPasswordReset(
            String phoneNumber, String smsCode) throws Exception {

        CustomerPhoneNumber customerPhoneNumber =
                customerPhoneNumberRepository.findByPhoneNumber(phoneNumber);

        if(customerPhoneNumber.getResetPasswordMessageCodeAttempt() > 3){
            throw new BadRequestException(ErrorMessage.ATTEMPT_NOT_ALLOWED.getErrorMessage());
        }

        if(!customerPhoneNumber.getResetPasswordMessageCode().equals(smsCode)){
            customerPhoneNumber.setResetPasswordMessageCodeAttempt(
                    customerPhoneNumber.getResetPasswordMessageCodeAttempt() + 1);
            customerPhoneNumberRepository.save(customerPhoneNumber);
            throw new BadRequestException(ErrorMessage.WRONG_MESSAGE_CODE.getErrorMessage());
        }

        customerPhoneNumber.setPasswordResetAllowed(true);
        customerPhoneNumberRepository.save(customerPhoneNumber);

        CustomerPhoneNumberDto customerPhoneNumberDto =
                customerPhoneNumberConverter.entityToDto(customerPhoneNumber);

        return customerPhoneNumberDto;
    }
}
