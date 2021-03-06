package com.flexbank.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CardDto {
    private Integer id;
    private String cardType;
    private String cardNumber;
    private String expiryDate;
    private Double balance;
    private String customerName;
    private Boolean isBlocked;
    private Boolean isExpired;
}
