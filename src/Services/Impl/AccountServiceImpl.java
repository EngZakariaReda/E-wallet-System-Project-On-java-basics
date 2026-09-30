package Services.Impl;
import Enums.DepositStatus;
import Enums.TransferStatus;
import Enums.WithdrawStatus;
import Exceptions.AccountNotExisted;
import Exceptions.SamePassword;
import Models.Account;
import Models.WalletSystem;
import Services.AccountService;
import java.util.Optional;
import java.util.Scanner;

public class AccountServiceImpl implements AccountService {

    private WalletSystem walletSystem = new WalletSystem();
    private Scanner scanner = new Scanner(System.in);

    @Override
    public Account createAccount(Account account) {
        Boolean isExisted = isUserNameExists(account.getUserName());

        if (!isExisted){
            walletSystem.getAccounts().add(account);
            return account;
        }

        return null ;
    }

    @Override
    public Account getAccountByUserNameAndPassword(Account account) {
        Optional<Account> existedAccount = walletSystem.getAccounts().
                stream()
                .filter(acc -> acc.getUserName().equals(account.getUserName())
                        && acc.getPassword().equals(account.getPassword()))
                .findFirst();

        return existedAccount.orElse(null);
    }

    @Override
    public DepositStatus  deposit(Account account , Double amount) {

        Account isExisted = getAccountByUsername(account.getUserName());
        if (isExisted == null){
            return DepositStatus.ACCOUNT_NOT_EXIST ;
        }

        if (!checkAllowedAmount(amount)) {
            return DepositStatus.INVALID_AMOUNT;
        }

        isExisted.setBalance(isExisted.getBalance() + amount);
        return DepositStatus.SUCCESS ;
    }

    @Override
    public WithdrawStatus  withdraw(Account account , Double amount) {

        Account isExisted = getAccountByUsername(account.getUserName());
        if (isExisted == null){
            return WithdrawStatus.ACCOUNT_NOT_EXIST ;
        }

        if (!checkAllowedAmount(amount)) {
            return WithdrawStatus.INVALID_AMOUNT;
        }

        if (isExisted.getBalance() < amount){
            return WithdrawStatus.INSUFFICIENT_BALANCE ;
        }

        isExisted.setBalance(isExisted.getBalance() - amount);
        return WithdrawStatus.SUCCESS ;
    }

    @Override
    public TransferStatus transfer(Account sender, Account receiver, Double amount) {
        Account isSenderExisted = getAccountByUsername(sender.getUserName());
        if (isSenderExisted == null){
            return TransferStatus.SENDER_ACCOUNT_NOT_EXIST ;
        }

        Account isReceiverExisted = getAccountByUsername(receiver.getUserName());
        if (isReceiverExisted == null){
            return TransferStatus.RECEIVER_ACCOUNT_NOT_EXIST ;
        }

        if ((isSenderExisted.getUserName().equals(isReceiverExisted.getUserName()))){
            return TransferStatus.SAME_ACCOUNT ;
        }

        if (!checkAllowedAmount(amount)) {
            return TransferStatus.INVALID_AMOUNT;
        }

        if (isSenderExisted.getBalance() < amount){
            return TransferStatus.INSUFFICIENT_BALANCE ;
        }

        isSenderExisted.setBalance(isSenderExisted.getBalance() - amount);
        isReceiverExisted.setBalance(isReceiverExisted.getBalance() + amount);
        return TransferStatus.SUCCESS;
    }

    @Override
    public void changePassword(Account account , String oldPassword , String newPassword) throws AccountNotExisted , SamePassword{
        Account isExisted = getAccountByUsername(account.getUserName());

        if (isExisted == null){
            throw new AccountNotExisted("account not existed");
        }

        if (oldPassword.equals(newPassword)){
            throw new SamePassword("old password equal to new password");
        }

        isExisted.setPassword(newPassword);
    }

    @Override
    public Double showBalance(Account account) throws AccountNotExisted{
        Account isExisted = getAccountByUsername(account.getUserName());

        if(isExisted == null){
            throw new AccountNotExisted("account not existed");
        }
        return isExisted.getBalance();
    }

    @Override
    public void showDetails(Account account) throws AccountNotExisted{
        Account isExisted = getAccountByUsername(account.getUserName());
        if(isExisted == null){
            throw new AccountNotExisted("account not existed");
        }

        System.out.println("Username: " + isExisted.getUserName());
        System.out.println("password: " + "*".repeat(isExisted.getPassword().length()));
        System.out.println("Phone: " + isExisted.getPhoneNumber());
        System.out.println("Balance: " + isExisted.getBalance());
        System.out.println("Age: " + isExisted.getAge());
    }

    @Override
    public Account getAccountByUsername(String userName) {
        Optional<Account> isExisted = walletSystem.getAccounts().stream()
                .filter(acc -> acc.getUserName().equals(userName))
                .findFirst();

        return isExisted.orElse(null);
    }

    @Override
    public Boolean checkAllowedAmount(Double value) {
        return value != null && value >= 100 && value <= 12000;
    }

    @Override
    public Boolean isUserNameExists(String userName) {
        return walletSystem.getAccounts().stream()
                .anyMatch(acc -> acc.getUserName().equals(userName));
    }

    @Override
    public Boolean checkUniqueNumber(String phone) {
        return walletSystem.getAccounts().stream()
                .anyMatch(acc -> acc.getPhoneNumber().equals(phone));
    }

}
