package com.flexbank.ws.repository;

import com.flexbank.ws.entity.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRequestRepository extends JpaRepository<LoanRequest, Integer> {
}
