package Services;
import Enums.DepositStatus;
import Enums.TransferStatus;
import Enums.WithdrawStatus;
import Exceptions.AccountNotExistedException;
import Exceptions.InvalidPasswordException;
import Exceptions.SamePasswordException;
import Models.Account;

import java.util.Optional;

public interface AccountService {
    Account createAccount (Account account);

    Account getAccountByUserNameAndPassword (Account account);

    DepositStatus deposit (Account account , Double amount);

    WithdrawStatus withdraw (Account account , Double amount);

    TransferStatus transfer (Account sender , Account receiver , Double amount );

    void changePassword (Account account , String newPassword , String oldPassword) throws AccountNotExistedException , InvalidPasswordException , SamePasswordException;

    Double showBalance (Account account) throws AccountNotExistedException;

    void showDetails (Account account) throws AccountNotExistedException;

    Account getAccountByUsername(String userName);

    Boolean checkAllowedAmount(Double value);

    Boolean isUserNameExists(String userName);

    Boolean checkUniqueNumber(String phone);

    void  viewAllAccounts(Account admin);

    void viewAccount(Account admin , String userName);

    void deleteAccount(Account admin , String userName);

    void activateAccount(Account admin , String userName);

    void deactivateAccount(Account admin , String userName);

    void viewTransactionHistory(Account admin , String userName);

    Boolean isAdmin(Account account);

    void checkAdminException(Account account);
}
