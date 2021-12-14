package com.flexbank.ws.configuration.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt")
@Data
@NoArgsConstructor
public class JwtConfiguration {
    private String tokenSecret;
}
