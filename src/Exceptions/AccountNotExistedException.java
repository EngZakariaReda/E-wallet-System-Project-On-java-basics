package Exceptions;

public class AccountNotExistedException extends RuntimeException {
    public AccountNotExistedException(String message) {
        super(message);
    }
}
