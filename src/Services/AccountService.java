package Services;

import Models.Account;

public interface AccountService {
    Account createAccount (Account account);

    Account getAccountByUserNameAndPassword (Account account);

    Account depositToAccount (Account account);

    Account withdrawFromAccount (Account account);

    Account transferFromAccountToAnother (Account account);

    Account changePasswordOfAccount (Account account);

    void showBalanceOfAccount (Account account);

    void showDetailsOfAccount (Account account);
}
