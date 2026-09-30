package Enums;

public enum DepositStatus {

    SUCCESS("Deposit completed successfully."),
    ACCOUNT_NOT_EXIST("Account does not exist."),
    INVALID_AMOUNT("Invalid deposit amount.");

    private final String message;

    DepositStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}