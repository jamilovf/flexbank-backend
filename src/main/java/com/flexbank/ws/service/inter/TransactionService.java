package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.TransactionDto;
import com.flexbank.ws.dto.request.ExternalTransferRequest;
import com.flexbank.ws.dto.request.InternalTransferRequest;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    List<TransactionDto> findAllByCustomerId(Integer customerId, int page, int limit);

    void transferInternal(InternalTransferRequest internalTransferRequest) throws Exception;

    void transferExternal(ExternalTransferRequest externalTransferRequest);

    Integer countPagesByCustomerId(Integer customerId);

    List<TransactionDto> searchTransactionsByDateAndType(Integer customerId,
                                                         LocalDate from, LocalDate to,
                                                         String type1, String type2,
                                                         int page, int limit);
}
