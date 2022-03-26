package com.flexbank.ws.client.sms;

public interface SmsSender {
     void sendSms(SmsRequest smsRequest);
     void sendSmsForPasswordReset(SmsRequest smsRequest);
}
