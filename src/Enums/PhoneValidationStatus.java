package Enums;

public enum PhoneValidationStatus {
    VALID("phone is valid"),
    INVALID_FORMAT("Invalid phone number format."),
    ALREADY_EXISTS("phone already exists.");

    private final String message;

    PhoneValidationStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

