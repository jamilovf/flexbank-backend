package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.TransactionDto;

import java.util.List;

public interface TransactionService {

    List<TransactionDto> findAllByCustomerId(Integer customerId);
}
