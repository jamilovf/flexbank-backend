package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.dto.request.SmsCodeRequest;

public interface CustomerPhoneNumberService {

    CustomerPhoneNumberDto findByPhoneNumber(String phoneNumber) throws Exception;

    CustomerPhoneNumberDto verifySmsCode(String phoneNumber, String smsCode) throws Exception;
}
