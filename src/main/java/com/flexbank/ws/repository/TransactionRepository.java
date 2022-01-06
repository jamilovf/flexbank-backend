package com.flexbank.ws.repository;

import com.flexbank.ws.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {

    Page<Transaction> findAllByCustomerId(Integer customerId, Pageable pageable);
    Integer countByCustomerId(Integer customerId);
    Page<Transaction> findAllByCreatedAtDateBetweenAndCustomerIdAndTypeIn(LocalDate from,
                                                                          LocalDate to,
                                                                        Integer customerId,
                                                                        List<String> types,
                                                                        Pageable pageable);
    Integer countByCreatedAtDateBetweenAndCustomerIdAndTypeIn(LocalDate from,
                                                              LocalDate to,
                                                                          Integer customerId,
                                                                          List<String> types);

}
