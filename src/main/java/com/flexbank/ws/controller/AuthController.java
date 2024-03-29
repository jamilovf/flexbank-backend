package com.flexbank.ws.controller;

import com.flexbank.ws.dto.CustomerDto;
import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.dto.request.PhoneNumberRequest;
import com.flexbank.ws.dto.request.ResetPasswordRequest;
import com.flexbank.ws.dto.request.SmsCodeRequest;
import com.flexbank.ws.service.inter.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

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

    @PostMapping("/verifyPhoneNumberForPasswordReset")
    public ResponseEntity<?> verifyPhoneNumberForPasswordReset(
            @RequestBody PhoneNumberRequest phoneNumberRequest) throws Exception {

        CustomerPhoneNumberDto customerPhoneNumberDto =
                authService.verifyPhoneNumberForPasswordReset(phoneNumberRequest.getPhoneNumber());

        return ResponseEntity.ok(customerPhoneNumberDto);
    }

    @PostMapping("/verifySmsCodeForPasswordReset")
    public ResponseEntity<?> verifySmsCodeForPasswordReset(
            @RequestBody SmsCodeRequest smsCodeRequest) throws Exception {

        CustomerPhoneNumberDto customerPhoneNumberDto =
                authService.verifySmsCodeForPasswordReset(smsCodeRequest.getPhoneNumber(),
                        smsCodeRequest.getSmsCode());

        return ResponseEntity.ok(customerPhoneNumberDto);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws Exception {
        authService.resetPassword(resetPasswordRequest.getEmail(),
                resetPasswordRequest.getNewPassword());

        return ResponseEntity.ok("Password is changed successfully!");
    }
}
