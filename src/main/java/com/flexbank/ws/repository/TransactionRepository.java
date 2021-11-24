package com.flexbank.ws.repository;

import com.flexbank.ws.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllByCustomerId(Integer customerId);
}
