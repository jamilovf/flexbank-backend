package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.dto.request.SmsCodeRequest;
import com.flexbank.ws.entity.CustomerPhoneNumber;

public interface CustomerPhoneNumberService {

    CustomerPhoneNumber findByPhoneNumber(String phoneNumber) throws Exception;

    CustomerPhoneNumberDto verifySmsCode(String phoneNumber, String smsCode) throws Exception;

    CustomerPhoneNumberDto verifySmsCodeForPasswordReset(
            String phoneNumber, String smsCode) throws Exception;
}
