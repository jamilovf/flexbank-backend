package com.flexbank.ws.configuration.ibanapi;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("ibanapi")
@Data
@NoArgsConstructor
public class IbanApiConfiguration {

    private String apiKey;
}
