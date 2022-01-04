package com.flexbank.ws.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UpdateCustomerRequest {
    private String city;
    private String address;
    private String zip;
    private String phoneNumber;
}
