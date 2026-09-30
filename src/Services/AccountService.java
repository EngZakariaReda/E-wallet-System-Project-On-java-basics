package Services;

import Enums.DepositStatus;
import Enums.TransferStatus;
import Enums.WithdrawStatus;
import Exceptions.AccountNotExisted;
import Exceptions.SamePassword;
import Models.Account;

import java.util.Optional;

public interface AccountService {
    Account createAccount (Account account);

    Account getAccountByUserNameAndPassword (Account account);

    DepositStatus deposit (Account account , Double amount);

    WithdrawStatus withdraw (Account account , Double amount);

    TransferStatus transfer (Account sender , Account receiver , Double amount );

    void changePassword (Account account , String newPassword , String oldPassword) throws AccountNotExisted , SamePassword;

    Double showBalance (Account account) throws AccountNotExisted;

    void showDetails (Account account) throws AccountNotExisted;

    Account getAccountByUsername(String userName);

    Boolean checkAllowedAmount(Double value);

    Boolean isUserNameExists(String userName);

    Boolean checkUniqueNumber(String phone);
}
