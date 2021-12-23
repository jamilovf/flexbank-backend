package com.flexbank.ws.dto;

import com.flexbank.ws.entity.LoanRequestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoanRequestDto {
    private Integer id;
    private LoanRequestType type;
    private Double amount;
    private int period;
}
