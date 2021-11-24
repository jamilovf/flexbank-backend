package com.flexbank.ws.service.impl;

import com.flexbank.ws.converter.TransactionConverter;
import com.flexbank.ws.dto.TransactionDto;
import com.flexbank.ws.entity.Transaction;
import com.flexbank.ws.repository.TransactionRepository;
import com.flexbank.ws.service.inter.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionConverter transactionConverter;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionConverter transactionConverter) {
        this.transactionRepository = transactionRepository;
        this.transactionConverter = transactionConverter;
    }

    @Override
    public List<TransactionDto> findAllByCustomerId(Integer customerId) {

        List<Transaction> transactions = transactionRepository.findAllByCustomerId(customerId);

        List<TransactionDto> transactionDtos =
                transactions.stream()
                .map(transaction -> transactionConverter.entityToDto(transaction))
                .collect(Collectors.toList());

        return transactionDtos;
    }
}
