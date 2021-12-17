package com.flexbank.ws.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConstants {
    private static JwtConfiguration jwtConfiguration;

    @Autowired
    public SecurityConstants(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public static final long EXPIRATION_TIME = 86400000; // 1 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String VERIFY_PHONE_NUMBER_URL = "/api/auth/verifyPhoneNumber";
    public static final String VERIFY_SMS_CODE_URL = "/api/auth/verifySmsCode";
    public static final String SIGNUP_URL = "/api/auth/signup";
    public static final String SIGNIN_URL = "/api/auth/signin";

    public static String getTokenSecret(){
        return jwtConfiguration.getTokenSecret();
    }
}
