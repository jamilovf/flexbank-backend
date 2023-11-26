package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.LoanNotificationDto;

import java.util.List;

public interface LoanNotificationService {

    List<LoanNotificationDto> findAllByCustomerId();
}
