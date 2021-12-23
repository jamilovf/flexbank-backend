package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.LoanRequestDto;

public interface LoanRequestService {

    void requestPersonalLoan(Integer customerId, LoanRequestDto loanRequestDto);
}
