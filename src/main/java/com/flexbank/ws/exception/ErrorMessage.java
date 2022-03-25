package com.flexbank.ws.exception;

public enum ErrorMessage {
    INVALID_IBAN("Invalid IBAN!"),
    INVALID_SWIFT("Invalid Swift code!"),
    EMAIL_ALREADY_REGISTERED("Email already is registered!"),
    INSUFFICIENT_BALANCE("Insufficient balance!"),
    PASSWORDS_MUST_EQUAL("Passwords must be equal!"),
    NOT_ALLOWED("Not Allowed!"),
    CUSTOMER_ALREADY_REGISTERED("Customer is already registered!"),
    WRONG_PHONE_NUMBER("No customer with this phone number!"),
    WRONG_MESSAGE_CODE("Wrong message code!"),
    RECIPIENT_NAME_ERROR("Recipient first or last name is wrong!"),
    WRONG_CARD_NUMBER("There is no card with this number!"),
    EXPIRED_CARD_BLOCK("Expired card cannot be blocked!"),
    CARD_ALREADY_BLOCKED("Card is already blocked!"),
    CARD_ALREADY_EXISTS("Ordered card already exists!"),
    CARD_ALREADY_UNBLOCKED("Card is already unblocked!"),
    ATTEMPT_NOT_ALLOWED("Your attempt is blocked! Please, call the Customer Service.");

    private String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
