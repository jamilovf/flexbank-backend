package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.CustomerDto;
import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    CustomerPhoneNumberDto verifyPhoneNumber(String phoneNumber);
    CustomerPhoneNumberDto verifySmsCode(String phoneNumber, String smsCode);
    void signup(CustomerDto customerDto);
}
