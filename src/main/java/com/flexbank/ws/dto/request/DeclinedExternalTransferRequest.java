package com.flexbank.ws.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeclinedExternalTransferRequest {
    private ExternalTransferRequest externalTransferRequest;
    private String declineReason;
}
