package com.flexbank.ws.repository;

import com.flexbank.ws.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {

    Page<Transaction> findAllByCustomerId(Integer customerId, Pageable pageable);
    Integer countByCustomerId(Integer customerId);


}
