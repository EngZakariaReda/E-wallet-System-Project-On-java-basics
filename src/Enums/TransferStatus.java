package Enums;

public enum TransferStatus {

    SUCCESS("Transfer completed successfully."),
    SENDER_ACCOUNT_NOT_EXIST("Sender account does not exist."),
    RECEIVER_ACCOUNT_NOT_EXIST("Receiver account does not exist."),
    INVALID_AMOUNT("Invalid transfer amount."),
    INSUFFICIENT_BALANCE("Insufficient balance."),
    SAME_ACCOUNT("You cannot transfer money to the same account.");

    private final String message;

    TransferStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}