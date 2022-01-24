package com.flexbank.ws.util;

public class LoanUtils {

    public static double calculateTotalRepaymentAmount(double amount, double rate, int period){

        double totalAmount =  amount * (Math.pow((1 + (rate / 100 / 12)), period));

        totalAmount = Double.parseDouble(String.format("%.2f", totalAmount));

        return totalAmount;
    }

}
