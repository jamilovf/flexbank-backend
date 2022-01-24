package com.flexbank.ws.repository;

import com.flexbank.ws.dto.LoanRequestNotificationDto;
import com.flexbank.ws.entity.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanRequestRepository extends JpaRepository<LoanRequest, Integer> {

    @Query(value = "select new com.flexbank.ws.dto.LoanRequestNotificationDto" +
            "(lr.id, lr.type, lr.amount, lr.period, c.firstName, c.lastName)" +
            " from LoanRequest lr left join Customer c on lr.customerId = c.id" +
            " where lr.status = 'UNDECIDED'")
    List<LoanRequestNotificationDto> getAllLoanRequestNotifications();
}
