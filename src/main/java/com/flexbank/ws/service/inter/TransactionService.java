package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.TransactionDto;
import com.flexbank.ws.dto.request.ExternalTransferRequest;
import com.flexbank.ws.dto.request.InternalTransferRequest;

import java.util.List;

public interface TransactionService {

    List<TransactionDto> findAllByCustomerId(int page, int limit);

    void transferInternal(InternalTransferRequest internalTransferRequest) throws Exception;

    void transferExternal(ExternalTransferRequest externalTransferRequest);

    Integer countPagesByCustomerId();

    List<TransactionDto> searchTransactionsByDateAndType(String from, String to,
                                                         String type1, String type2,
                                                         int page, int limit);

    void payLoan(Integer loanId, Integer cardId) throws Exception;
}
