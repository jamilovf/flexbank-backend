package com.flexbank.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;

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
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String resetPasswordMessageCode;
    private boolean isResetPasswordMessageCodeAllowed;
    private boolean isPasswordResetAllowed;

}
