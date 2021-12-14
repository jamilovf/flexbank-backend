package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.CustomerPhoneNumberDto;
import com.flexbank.ws.dto.request.SmsCodeRequest;

public interface CustomerPhoneNumberService {

    CustomerPhoneNumberDto findByPhoneNumber(String phoneNumber);

    CustomerPhoneNumberDto verifySmsCode(String phoneNumber, String smsCode);
}
