package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.CustomerDto;
import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.entity.Customer;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    CustomerPhoneNumberDto verifyPhoneNumber(String phoneNumber) throws Exception;

    CustomerPhoneNumberDto verifySmsCode(String phoneNumber, String smsCode) throws Exception;

    void signup(CustomerDto customerDto) throws Exception;

    Customer getCustomer(String email);

    CustomerPhoneNumberDto verifyPhoneNumberForPasswordReset(String phoneNumber) throws Exception;

    CustomerPhoneNumberDto verifySmsCodeForPasswordReset(String phoneNumber, String smsCode) throws Exception;

    void resetPassword(String email, String newPassword) throws Exception;
}
