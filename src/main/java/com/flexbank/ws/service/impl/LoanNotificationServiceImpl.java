package com.flexbank.ws.service.impl;

import com.flexbank.ws.converter.LoanNotificationConverter;
import com.flexbank.ws.dto.LoanNotificationDto;
import com.flexbank.ws.entity.LoanNotification;
import com.flexbank.ws.repository.LoanNotificationRepository;
import com.flexbank.ws.service.inter.LoanNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanNotificationServiceImpl implements LoanNotificationService {

    private final LoanNotificationRepository loanNotificationRepository;

    private final LoanNotificationConverter loanNotificationConverter;

    @Override
    public List<LoanNotificationDto> findAllByCustomerId(Integer customerId) {

        List<LoanNotification> loanNotifications = loanNotificationRepository.findAllByCustomerId(customerId);

        List<LoanNotificationDto> loanNotificationDtos =
                loanNotifications.stream()
                        .filter(loanNotification ->
                                loanNotification.getDueTo().getMonth() == LocalDate.now().getMonth() &&
                                        !loanNotification.getIsPaid())
                        .map(loanNotification -> loanNotificationConverter.entityToDto(loanNotification))
                        .collect(Collectors.toList());

        return loanNotificationDtos;
    }
}
