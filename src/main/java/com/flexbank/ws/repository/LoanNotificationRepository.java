package com.flexbank.ws.repository;

import com.flexbank.ws.entity.LoanNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LoanNotificationRepository extends JpaRepository<LoanNotification, Integer> {

    List<LoanNotification> findAllByCustomerId(Integer customerId);

    @Query(value = "select new com.flexbank.ws.entity.LoanNotification" +
            "(ln.id, ln.amount, ln.dueTo, ln.interestRate, ln.type, " +
            "ln.customerId, ln.isPaid)" +
            " from LoanNotification ln" +
            " where ln.dueTo < CURRENT_DATE and ln.isPaid = 'false'")
    List<LoanNotification> findAllLessThanToday();
}
