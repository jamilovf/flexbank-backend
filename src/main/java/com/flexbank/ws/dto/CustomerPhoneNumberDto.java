package com.flexbank.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerPhoneNumberDto {

    private Integer id;
    private String phoneNumber;
    private boolean isRegistered;
    private String messageCode;
    private boolean isMessageCodeAllowed;
    private boolean isSignupAllowed;

}
