package com.flexbank.ws.client.twilio;

import com.flexbank.ws.client.sms.SmsRequest;
import com.flexbank.ws.client.sms.SmsSender;
import com.flexbank.ws.configuration.twilio.TwilioConfiguration;
import com.flexbank.ws.entity.CustomerPhoneNumber;
import com.flexbank.ws.repository.CustomerPhoneNumberRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class TwilioSmsSender implements SmsSender {

    private final TwilioConfiguration twilioConfiguration;

    private final CustomerPhoneNumberRepository customerPhoneNumberRepository;


    @Override
    public void sendSms(SmsRequest smsRequest) {
        PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
        String message = smsRequest.getMessage();

        Random random = new Random();
        String messageCode = String.valueOf(random.nextInt(twilioConfiguration.getBound()) +
                twilioConfiguration.getStart());
        message = message + messageCode;

        CustomerPhoneNumber customerPhoneNumber =
                customerPhoneNumberRepository.findByPhoneNumber(smsRequest.getPhoneNumber());

        customerPhoneNumber.setMessageCode(messageCode);
        customerPhoneNumber.setMessageCodeAllowed(true);

        customerPhoneNumberRepository.save(customerPhoneNumber);

        MessageCreator messageCreator = Message.creator(to, from, message);
        messageCreator.create();
    }

    @Override
    public void sendSmsForPasswordReset(SmsRequest smsRequest) {
        PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
        String message = smsRequest.getMessage();

        Random random = new Random();
        String messageCode = String.valueOf(random.nextInt(twilioConfiguration.getBound()) +
                twilioConfiguration.getStart());
        message = message + messageCode;

        CustomerPhoneNumber customerPhoneNumber =
                customerPhoneNumberRepository.findByPhoneNumber(smsRequest.getPhoneNumber());

        customerPhoneNumber.setResetPasswordMessageCode(messageCode);
        customerPhoneNumber.setResetPasswordMessageCodeAllowed(true);

        customerPhoneNumberRepository.save(customerPhoneNumber);

        MessageCreator messageCreator = Message.creator(to, from, message);
        messageCreator.create();
    }
}
