package Enums;

public enum PasswordValidationStatus {
    VALID("password is valid."),
    TOO_SHORT("Password must be at least 6 characters."),
    TOO_LONG("Password must not exceed 20 characters."),
    INVALID_FORMAT("Password cannot be empty."),
    NO_UPPERCASE("Password must contain an uppercase letter."),
//    NO_LOWERCASE("Password must contain a lowercase letter."),
    NO_NUMBER("Password must contain a number.");

    private final String message;

    PasswordValidationStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
