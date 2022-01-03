package com.flexbank.ws.configuration.bankdetails;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("flexbank")
@Data
@NoArgsConstructor
public class BankDetailsConfiguration {
    private String MII;
    private String BIN;
}
