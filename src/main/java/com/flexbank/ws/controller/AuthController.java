package com.flexbank.ws.controller;

import com.flexbank.ws.configuration.sms.SmsRequest;
import com.flexbank.ws.configuration.sms.SmsSender;
import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.dto.request.PhoneNumberRequest;
import com.flexbank.ws.dto.request.SmsCodeRequest;
import com.flexbank.ws.service.inter.CustomerPhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomerPhoneNumberService customerPhoneNumberService;
    private final SmsSender smsSender;

    @Autowired
    public AuthController(CustomerPhoneNumberService customerPhoneNumberService,
                          SmsSender smsSender) {
        this.customerPhoneNumberService = customerPhoneNumberService;
        this.smsSender = smsSender;
    }

    @PostMapping("/verifyPhoneNumber")
    public ResponseEntity<?> verifyPhoneNumber(
            @RequestBody PhoneNumberRequest phoneNumberRequest){

        CustomerPhoneNumberDto customerPhoneNumberDto =
                customerPhoneNumberService.findByPhoneNumber(phoneNumberRequest.getPhoneNumber());

        SmsRequest smsRequest = new SmsRequest(phoneNumberRequest.getPhoneNumber() ,
                "Welcome to FlexBank!\n Your verification code: ");
        smsSender.sendSms(smsRequest);

        return ResponseEntity.ok(customerPhoneNumberDto);
    }

    @PostMapping("/verifySmsCode")
    public ResponseEntity<?> verifySmsCode(
            @RequestBody SmsCodeRequest smsCodeRequest){

        CustomerPhoneNumberDto customerPhoneNumberDto =
                customerPhoneNumberService.verifySmsCode(smsCodeRequest);

        return ResponseEntity.ok(customerPhoneNumberDto);
    }
}
