package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.TransactionDto;
import com.flexbank.ws.dto.request.ExternalTransferRequest;
import com.flexbank.ws.dto.request.InternalTransferRequest;

import java.util.List;

public interface TransactionService {

    List<TransactionDto> findAllByCustomerId(Integer customerId);

    void transferInternal(InternalTransferRequest internalTransferRequest) throws Exception;

    void transferExternal(ExternalTransferRequest externalTransferRequest);
}
