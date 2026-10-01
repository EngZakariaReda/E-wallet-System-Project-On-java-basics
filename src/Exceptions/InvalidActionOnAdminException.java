package Exceptions;

public class InvalidActionOnAdminException extends RuntimeException {
    public InvalidActionOnAdminException(String message) {
        super(message);
    }
}
