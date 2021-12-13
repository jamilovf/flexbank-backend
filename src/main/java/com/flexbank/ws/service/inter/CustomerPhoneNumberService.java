package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.CustomerPhoneNumberDto;

public interface CustomerPhoneNumberService {

    CustomerPhoneNumberDto findByPhoneNumber(String phoneNumber);
}
