package com.flexbank.ws.service.inter;

import com.flexbank.ws.dto.LoanRequestDto;

public interface LoanRequestService {

    void requestPersonalLoan(LoanRequestDto loanRequestDto);
    void requestCarLoan(LoanRequestDto loanRequestDto);

}
