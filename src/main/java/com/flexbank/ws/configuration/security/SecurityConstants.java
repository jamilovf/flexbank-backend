package com.flexbank.ws.configuration.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 86400000; // 1 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String VERIFY_PHONE_NUMBER_URL = "/api/auth/verifyPhoneNumber";
    public static final String VERIFY_SMS_CODE_URL = "/api/auth/verifySmsCode";
    public static final String SIGNUP_URL = "/api/auth/signup";
    public static final String SIGNIN_URL = "/api/auth/signin";

    public static String getTokenSecret(){
        JwtConfiguration jwtConfiguration = new JwtConfiguration();
        return jwtConfiguration.getTokenSecret();
    }
}
