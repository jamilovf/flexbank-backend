package com.flexbank.ws.service.impl;

import com.flexbank.ws.entity.LoanNotification;
import com.flexbank.ws.repository.LoanNotificationRepository;
import com.flexbank.ws.service.LoanNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanNotificationServiceImpl implements LoanNotificationService {

    private final LoanNotificationRepository loanNotificationRepository;

    @Override
    public List<LoanNotification> findAllByCustomerId(Integer customerId) {
        return loanNotificationRepository.findAllByCustomerId(customerId);
    }
}
