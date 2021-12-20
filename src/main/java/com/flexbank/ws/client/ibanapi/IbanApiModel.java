package com.flexbank.ws.client.ibanapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IbanApiModel {
    private String result;
    private String swiftCode;
}
