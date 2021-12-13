package com.flexbank.ws.configuration.sms;

import lombok.Data;

@Data
public class SmsRequest {
    private final String phoneNumber;
    private final String message;
}
