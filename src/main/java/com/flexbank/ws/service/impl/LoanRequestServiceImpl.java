package com.flexbank.ws.service.impl;

import com.flexbank.ws.converter.LoanRequestConverter;
import com.flexbank.ws.dto.LoanRequestDto;
import com.flexbank.ws.entity.LoanRequest;
import com.flexbank.ws.entity.LoanRequestType;
import com.flexbank.ws.repository.LoanRequestRepository;
import com.flexbank.ws.service.inter.LoanRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanRequestServiceImpl implements LoanRequestService {

    private final LoanRequestRepository loanRequestRepository;
    private final LoanRequestConverter loanRequestConverter;

    @Autowired
    public LoanRequestServiceImpl(LoanRequestRepository loanRequestRepository,
                                  LoanRequestConverter loanRequestConverter) {
        this.loanRequestRepository = loanRequestRepository;
        this.loanRequestConverter = loanRequestConverter;
    }

    @Override
    public void requestPersonalLoan(Integer customerId, LoanRequestDto loanRequestDto) {

        loanRequestDto.setType(LoanRequestType.PERSONAL_LOAN);

        LoanRequest loanRequest = loanRequestConverter.dtoToEntity(loanRequestDto);

        loanRequest.setCustomerId(customerId);

        loanRequestRepository.save(loanRequest);
    }
}
