package com.flexbank.ws.scheduler;

import com.flexbank.ws.entity.LoanNotification;
import com.flexbank.ws.repository.LoanNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class LoanNotificationScheduler {

    private final LoanNotificationRepository loanNotificationRepository;

    @Autowired
    public LoanNotificationScheduler(LoanNotificationRepository loanNotificationRepository) {
        this.loanNotificationRepository = loanNotificationRepository;
    }

    @Scheduled(cron = "0 1 0 * * ?")
    public void penalizeDelayedLoanPayments(){
        List<LoanNotification> delayedLoanNotifications =
                loanNotificationRepository.findAllLessThanToday();

        delayedLoanNotifications.forEach(loanNotification ->
                        loanNotification.setAmount(loanNotification.getAmount()
                                + loanNotification.getAmount() * 0.01));

        loanNotificationRepository.saveAll(delayedLoanNotifications);
    }
}
