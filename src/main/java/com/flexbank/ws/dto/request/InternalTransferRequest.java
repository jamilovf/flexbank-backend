package com.flexbank.ws.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InternalTransferRequest {
    private String recipientCardNumber;
    private String firstName;
    private String lastName;
    private Double amount;
    private String chosenCard;
}
