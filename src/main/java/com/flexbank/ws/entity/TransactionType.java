package com.flexbank.ws.entity;

public enum TransactionType {
    INTERNAL_TRANSFER("Internal Transfer"), EXTERNAL_TRANSFER("Internal Transfer"), PAYMENT("Payment");

    private String text;

    TransactionType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
