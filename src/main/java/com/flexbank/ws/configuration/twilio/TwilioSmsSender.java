package com.flexbank.ws.configuration.twilio;

import com.flexbank.ws.configuration.sms.SmsRequest;
import com.flexbank.ws.configuration.sms.SmsSender;
import com.flexbank.ws.entity.CustomerPhoneNumber;
import com.flexbank.ws.repository.CustomerPhoneNumberRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TwilioSmsSender implements SmsSender {

    private final TwilioConfiguration twilioConfiguration;

    private final CustomerPhoneNumberRepository customerPhoneNumberRepository;

    @Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration,
                           CustomerPhoneNumberRepository customerPhoneNumberRepository) {
        this.twilioConfiguration = twilioConfiguration;
        this.customerPhoneNumberRepository = customerPhoneNumberRepository;
    }

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
        customerPhoneNumberRepository.save(customerPhoneNumber);

        MessageCreator messageCreator = Message.creator(to, from, message);
        messageCreator.create();
    }
}
