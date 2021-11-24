package com.flexbank.ws.service.impl;

import com.flexbank.ws.converter.LoanNotificationConverter;
import com.flexbank.ws.dto.LoanNotificationDto;
import com.flexbank.ws.entity.LoanNotification;
import com.flexbank.ws.repository.LoanNotificationRepository;
import com.flexbank.ws.service.inter.LoanNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class LoanNotificationServiceImpl implements LoanNotificationService {

    private final LoanNotificationRepository loanNotificationRepository;

    private final LoanNotificationConverter loanNotificationConverter;

    @Autowired
    public LoanNotificationServiceImpl(LoanNotificationRepository loanNotificationRepository, LoanNotificationConverter loanNotificationConverter) {
        this.loanNotificationRepository = loanNotificationRepository;
        this.loanNotificationConverter = loanNotificationConverter;
    }

    @Override
    public List<LoanNotificationDto> findAllByCustomerId(Integer customerId) {

        List<LoanNotification> loanNotifications = loanNotificationRepository.findAllByCustomerId(customerId);

        List<LoanNotificationDto> loanNotificationDtos =
                loanNotifications.stream()
                        .map(loanNotification -> loanNotificationConverter.entityToDto(loanNotification))
                        .collect(Collectors.toList());

        return loanNotificationDtos;
    }
}
