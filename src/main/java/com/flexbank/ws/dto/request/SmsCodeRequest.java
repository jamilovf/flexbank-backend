package com.flexbank.ws.dto.request;

import lombok.Data;

@Data
public class SmsCodeRequest {
    private String smsCode;
    private String phoneNumber;
}
