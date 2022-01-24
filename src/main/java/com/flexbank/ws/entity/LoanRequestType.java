package com.flexbank.ws.entity;

public enum LoanRequestType {
    PERSONAL(12), CAR(14);

    private double rate;

    LoanRequestType(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}
