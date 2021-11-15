package com.flexbank.ws.service;

import com.flexbank.ws.entity.Customer;
import com.flexbank.ws.entity.LoanNotification;

import java.util.List;

public interface LoanNotificationService {

    List<LoanNotification> findAllByCustomerId(Integer customerId);
}
