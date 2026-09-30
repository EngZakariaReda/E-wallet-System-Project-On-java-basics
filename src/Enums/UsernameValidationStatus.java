package Enums;

public enum UsernameValidationStatus {
    VALID("Username is valid."),
    INVALID_FORMAT("Username cannot be empty."),
    TOO_SHORT("Username must be at least 3 characters."),
    TOO_LONG("Username must not exceed 15 characters."),
    FIRST_LETTER_NOT_UPPERCASE("The first letter must be uppercase."),
    ALREADY_EXISTS("Username already exists.");

    private final String message;

    UsernameValidationStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
