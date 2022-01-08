package com.flexbank.ws.util;

import java.util.Random;

public class CustomerUtils {

    public static String generateAccountNumber(){
        Random r = new Random();
        int accountNumber = 100000000 + r.nextInt(900000000);

        return String.valueOf(accountNumber);
    }
}
