package com.flexbank.ws.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExternalTransferRequest {
    private String iban;
    private String swiftCode;
    private String recipientName;
    private Double amount;
    private String chosenCard;
}
