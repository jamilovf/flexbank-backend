package com.flexbank.ws.configuration.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyTokenUser {
    private String username;
    private Integer id;
}
