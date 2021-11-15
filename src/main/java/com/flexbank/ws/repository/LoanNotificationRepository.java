package com.flexbank.ws.repository;

import com.flexbank.ws.entity.LoanNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanNotificationRepository extends JpaRepository<LoanNotification,Integer> {

    List<LoanNotification> findAllByCustomerId(Integer customerId);
}
