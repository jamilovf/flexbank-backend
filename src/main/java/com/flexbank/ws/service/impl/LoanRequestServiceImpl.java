package com.flexbank.ws.service.impl;

import com.flexbank.ws.configuration.security.SecurityContextService;
import com.flexbank.ws.converter.LoanRequestConverter;
import com.flexbank.ws.dto.LoanRequestDto;
import com.flexbank.ws.entity.LoanRequest;
import com.flexbank.ws.entity.LoanRequestType;
import com.flexbank.ws.repository.LoanRequestRepository;
import com.flexbank.ws.service.inter.LoanRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanRequestServiceImpl implements LoanRequestService {

    private final LoanRequestRepository loanRequestRepository;
    private final LoanRequestConverter loanRequestConverter;

    @Override
    public void requestPersonalLoan(LoanRequestDto loanRequestDto) {

        Integer customerId = SecurityContextService.getCurrentCustomerId();

        loanRequestDto.setType(LoanRequestType.PERSONAL);

        LoanRequest loanRequest = loanRequestConverter.dtoToEntity(loanRequestDto);

        loanRequest.setCustomerId(customerId);

        loanRequestRepository.save(loanRequest);
    }

    @Override
    public void requestCarLoan(LoanRequestDto loanRequestDto) {

        Integer customerId = SecurityContextService.getCurrentCustomerId();

        loanRequestDto.setType(LoanRequestType.CAR);

        LoanRequest loanRequest = loanRequestConverter.dtoToEntity(loanRequestDto);

        loanRequest.setCustomerId(customerId);

        loanRequestRepository.save(loanRequest);
    }
}
