package Enums;

public enum WithdrawStatus {

    SUCCESS("Withdrawal completed successfully."),
    ACCOUNT_NOT_EXIST("Account does not exist."),
    INVALID_AMOUNT("Invalid withdrawal amount."),
    INSUFFICIENT_BALANCE("Insufficient balance.");

    private final String message;

    WithdrawStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}