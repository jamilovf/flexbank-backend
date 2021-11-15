package com.flexbank.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoanNotificationDto {

    private Integer id;
    private int amount;
    private LocalDate dueTo;
    private int interestRate;
    private String type;

}
