package com.flexbank.ws.dto.request;

import lombok.Data;

@Data
public class LoanPaymentRequest {
    private Integer loanId;
    private Integer cardId;
}
