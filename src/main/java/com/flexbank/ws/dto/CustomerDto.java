package com.flexbank.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerDto {

    @NotBlank(message = "Email field cannot be empty")
    @Email(message = "Please, enter valid email")
    @NotNull
    private String email;

    @NotBlank(message = "Password field cannot be empty")
    @NotNull
    private String password;

    private String passwordConfirmation;

    private String phoneNumber;
}
