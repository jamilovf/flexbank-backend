package com.flexbank.ws.util;

public class CardUtils {

    public static String generateCardNumber(String MII, String BIN, String accountNumber){
        String cardNumber = MII + BIN + accountNumber;

        String checkSum = findChecksumWithLuhn(cardNumber);

        cardNumber += checkSum;

        return cardNumber;
    }

    private static String findChecksumWithLuhn(String cardNumber){
        int sum = 0;

        for(int i = 0; i < cardNumber.length(); i++){
            if(i % 2 != 0){
                int doubledDigit = (int) (Character.getNumericValue(cardNumber.charAt(i)) * 2);
                if(doubledDigit >= 10){
                    sum += sumTwoDigitNumber(doubledDigit);
                }
                else{
                    sum += doubledDigit;
                }
            }
            else{
                sum += (int) Character.getNumericValue(cardNumber.charAt(i));
            }
        }

        int checksum = sum % 10;

        return String.valueOf(10 - checksum);
    }

    private static int sumTwoDigitNumber(int num) {
        int sum = 0;

        while (num != 0){
            sum += num % 10;
            num /= 10;
        }

        return sum;
    }
}
