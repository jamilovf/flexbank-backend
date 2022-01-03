package com.flexbank.ws.dto.request;

import lombok.Data;

@Data
public class CardOrderRequest {
    private Integer customerId;
    private String type;
}
