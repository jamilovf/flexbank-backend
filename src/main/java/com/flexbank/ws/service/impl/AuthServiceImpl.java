package com.flexbank.ws.service.impl;

import com.flexbank.ws.configuration.sms.SmsRequest;
import com.flexbank.ws.configuration.sms.SmsSender;
import com.flexbank.ws.converter.CustomerConverter;
import com.flexbank.ws.dto.CustomerDto;
import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.entity.Customer;
import com.flexbank.ws.service.inter.AuthService;
import com.flexbank.ws.service.inter.CustomerPhoneNumberService;
import com.flexbank.ws.service.inter.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final CustomerPhoneNumberService customerPhoneNumberService;
    private final SmsSender smsSender;
    private final CustomerService customerService;
    private final CustomerConverter customerConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthServiceImpl(CustomerPhoneNumberService customerPhoneNumberService,
                           SmsSender smsSender,
                           CustomerService customerService,
                           CustomerConverter customerConverter,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerPhoneNumberService = customerPhoneNumberService;
        this.smsSender = smsSender;
        this.customerService = customerService;
        this.customerConverter = customerConverter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public CustomerPhoneNumberDto verifyPhoneNumber(String phoneNumber) {
        CustomerPhoneNumberDto customerPhoneNumberDto =
                customerPhoneNumberService.findByPhoneNumber(phoneNumber);

        SmsRequest smsRequest = new SmsRequest(phoneNumber,
                "Welcome to FlexBank!\n Your verification code: ");
        smsSender.sendSms(smsRequest);

        return customerPhoneNumberDto;
    }

    @Override
    public CustomerPhoneNumberDto verifySmsCode(String phoneNumber, String smsCode) {
        CustomerPhoneNumberDto customerPhoneNumberDto =
                customerPhoneNumberService.verifySmsCode(phoneNumber, smsCode);

        return customerPhoneNumberDto;
    }

    @Override
    public void signup(CustomerDto customerDto) {
       customerService.findByEmail(customerDto.getEmail());

       if (!customerDto.getPassword().equals(customerDto.getPasswordConfirmation())){
           throw new RuntimeException("Passwords must be equal!");
       }

       Customer customer = customerConverter.dtoToEntity(customerDto);
       customer.setPassword(bCryptPasswordEncoder.encode(customerDto.getPassword()));

       customerService.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return null;
    }
}
