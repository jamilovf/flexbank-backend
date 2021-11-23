package com.flexbank.ws.service;

import com.flexbank.ws.dto.LoanNotificationDto;

import java.util.List;

public interface LoanNotificationService {

    List<LoanNotificationDto> findAllByCustomerId(Integer customerId);
}
