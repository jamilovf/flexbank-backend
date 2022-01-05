package com.flexbank.ws.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchRequest {
    private LocalDate from;
    private LocalDate to;
    private String type1;
    private String type2;
}
