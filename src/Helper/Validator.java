package Helper;
import Enums.PasswordValidationStatus;
import Enums.PhoneValidationStatus;
import Enums.UsernameValidationStatus;
import Services.Impl.AccountServiceImpl;

public class Validator {

    private AccountServiceImpl accountServiceImpl;

    public Validator(AccountServiceImpl accountService) {
        this.accountServiceImpl = accountService;
    }

    public UsernameValidationStatus isValidUsername(String userName) {
        if (userName == null || userName.isBlank()) {
            return UsernameValidationStatus.INVALID_FORMAT;
        }

        if (userName.length() < 3) {
            return UsernameValidationStatus.TOO_SHORT;
        }

        if (userName.length() > 20) {
            return UsernameValidationStatus.TOO_LONG;
        }

        if (!Character.isUpperCase(userName.charAt(0))) {
            return UsernameValidationStatus.FIRST_LETTER_NOT_UPPERCASE;
        }

        if (accountServiceImpl.isUserNameExists(userName)) {
            return UsernameValidationStatus.ALREADY_EXISTS;
        }

        return UsernameValidationStatus.VALID;
    }

    public static PasswordValidationStatus isValidPassword(String password) {
        if (password == null || password.isBlank()) {
            return PasswordValidationStatus.INVALID_FORMAT;
        }

        if (password.length() < 6) {
            return PasswordValidationStatus.TOO_SHORT;
        }

        if (password.length() > 20) {
            return PasswordValidationStatus.TOO_LONG;
        }

        if (!password.matches(".*[A-Z].*")) {
            return PasswordValidationStatus.NO_UPPERCASE;
        }

        if (!password.matches(".*\\d.*")) {
            return PasswordValidationStatus.NO_NUMBER;
        }

        return PasswordValidationStatus.VALID;
    }

    public PhoneValidationStatus isValidPhone(String phone) {
        if (!phone.matches("01\\d{9}")) {
            return PhoneValidationStatus.INVALID_FORMAT;
        }

        if (accountServiceImpl.checkUniqueNumber(phone)) {
            return PhoneValidationStatus.ALREADY_EXISTS;
        }

        return PhoneValidationStatus.VALID;
    }

}
