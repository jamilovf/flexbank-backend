package com.flexbank.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TransactionDto {
    private Double amount;
    private String description;
    private String type;
    private LocalDate createdAtDate;
    private LocalTime createdAtTime;
    private int page;
    private int limit;
    private int count;
}
