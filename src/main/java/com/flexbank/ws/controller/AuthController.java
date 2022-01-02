package com.flexbank.ws.controller;

import com.flexbank.ws.dto.CustomerDto;
import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.dto.request.PhoneNumberRequest;
import com.flexbank.ws.dto.request.SmsCodeRequest;
import com.flexbank.ws.service.inter.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/verifyPhoneNumber")
    public ResponseEntity<?> verifyPhoneNumber(
            @RequestBody PhoneNumberRequest phoneNumberRequest) throws Exception {

        CustomerPhoneNumberDto customerPhoneNumberDto =
                authService.verifyPhoneNumber(phoneNumberRequest.getPhoneNumber());

        return ResponseEntity.ok(customerPhoneNumberDto);
    }

    @PostMapping("/verifySmsCode")
    public ResponseEntity<?> verifySmsCode(@RequestBody SmsCodeRequest smsCodeRequest) throws Exception {

        CustomerPhoneNumberDto customerPhoneNumberDto =
                authService.verifySmsCode(smsCodeRequest.getPhoneNumber(),
                        smsCodeRequest.getSmsCode());

        return ResponseEntity.ok(customerPhoneNumberDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody CustomerDto customerDto) throws Exception {

        authService.signup(customerDto);

        return ResponseEntity.ok("Successful signup!");
    }
}
