package Services.Impl;
import Enums.*;
import Exceptions.*;
import Helper.Validator;
import Models.Account;
import Models.Transaction;
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
            account.getTransactions().add(new Transaction(
                    TransactionType.SIGNUP,
                    0.0,
                    "Account created"
                    )
            );
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

        if (existedAccount.isPresent()){
            existedAccount.get().getTransactions().add(new Transaction(
                            TransactionType.LOGIN,
                            0.0,
                            "login success"
                    )
            );
            return existedAccount.get();
        }

        return null;
    }

    @Override
    public DepositStatus deposit(Account account , Double amount) {

        Account isExisted = getAccountByUsername(account.getUserName());
        if (isExisted == null){
            return DepositStatus.ACCOUNT_NOT_EXIST ;
        }

        if (!checkAllowedAmount(amount)) {
            return DepositStatus.INVALID_AMOUNT;
        }

        isExisted.setBalance(isExisted.getBalance() + amount);
        isExisted.getTransactions().add(new Transaction(
                        TransactionType.DEPOSIT,
                         amount,
                "Money deposited"
                )
        );
        return DepositStatus.SUCCESS ;
    }

    @Override
    public WithdrawStatus withdraw(Account account , Double amount) {

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
        isExisted.getTransactions().add(
                new Transaction(
                        TransactionType.WITHDRAW,
                        amount,
                        "Money withdrawn"
                )
        );
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
        isSenderExisted.getTransactions().add(
                new Transaction(
                        TransactionType.TRANSFER,
                        amount,
                        "Transferred to " + isReceiverExisted.getUserName()
                )
        );

        isReceiverExisted.setBalance(isReceiverExisted.getBalance() + amount);
        isSenderExisted.getTransactions().add(
                new Transaction(
                        TransactionType.TRANSFER,
                        amount,
                        "Received from " + isSenderExisted.getUserName()
                )
        );

        return TransferStatus.SUCCESS;
    }

    @Override
    public void changePassword(Account account , String oldPassword , String newPassword)
            throws AccountNotExistedException , InvalidPasswordException , SamePasswordException {

        Account isExisted = getAccountByUsername(account.getUserName());

        if (isExisted == null){
            throw new AccountNotExistedException("account not existed");
        }

        PasswordValidationStatus status = Validator.isValidPassword(newPassword);
        if (status != PasswordValidationStatus.VALID) {
            throw new InvalidPasswordException(status.getMessage());
        }

        if (oldPassword.equals(newPassword)){
            throw new SamePasswordException("old password equal to new password");
        }

        isExisted.setPassword(newPassword);
    }

    @Override
    public Double showBalance(Account account) throws AccountNotExistedException {
        Account isExisted = getAccountByUsername(account.getUserName());

        if(isExisted == null){
            throw new AccountNotExistedException("account not existed");
        }
        return isExisted.getBalance();
    }

    @Override
    public void showDetails(Account account) throws AccountNotExistedException{
        Account isExisted = getAccountByUsername(account.getUserName());
        if(isExisted == null){
            throw new AccountNotExistedException("account not existed");
        }

        System.out.println("Username: " + isExisted.getUserName());
        System.out.println("password: " + "*".repeat(isExisted.getPassword().length()));
        System.out.println("Phone: " + isExisted.getPhoneNumber());
        System.out.println("Balance: " + isExisted.getBalance());
        System.out.println("Age: " + isExisted.getAge());
        System.out.println("Status: " + isExisted.getActive());
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

    @Override
    public void viewAllAccounts(Account admin) {
        checkAdminException(admin);

        for (Account account : walletSystem.getAccounts()) {
            System.out.println("-------------------------");
            System.out.println("Username: " + account.getUserName());
            System.out.println("password: " + "*".repeat(account.getPassword().length()));
            System.out.println("Phone: " + account.getPhoneNumber());
            System.out.println("Age: " + account.getAge());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("Status: " + account.getActive());
        }
    }

    @Override
    public void viewAccount(Account admin , String userName) {
        checkAdminException(admin);

        Account isExisted = getAccountByUsername(userName);
        if(isExisted == null){
            throw new AccountNotExistedException("account not existed");
        }

        System.out.println("Username: " + isExisted.getUserName());
        System.out.println("password: " + "*".repeat(isExisted.getPassword().length()));
        System.out.println("Phone: " + isExisted.getPhoneNumber());
        System.out.println("Balance: " + isExisted.getBalance());
        System.out.println("Age: " + isExisted.getAge());
        System.out.println("Status: " + (isExisted.getActive() ? "active" : "inactive"));
    }

    @Override
    public void deleteAccount(Account admin , String userName) {
        checkAdminException(admin);

        Account isExisted = getAccountByUsername(userName);
        if (isExisted == null){
            throw new AccountNotExistedException("account not existed");
        }

        if (isExisted.getAdmin()){
            throw new InvalidActionOnAdminException("admin can not be deleted");
        }

        walletSystem.getAccounts().remove(isExisted);
    }

    @Override
    public void activateAccount(Account admin , String userName) {
        checkAdminException(admin);

        Account isExisted = getAccountByUsername(userName);
        if (isExisted == null){
            throw new AccountNotExistedException("account not existed");
        }

        isExisted.setActive(true);
    }

    @Override
    public void deactivateAccount(Account admin , String userName) {
        checkAdminException(admin);

        Account isExisted = getAccountByUsername(userName);
        if (isExisted == null){
            throw new AccountNotExistedException("account not existed");
        }

        if (isExisted.getAdmin()){
            throw new InvalidActionOnAdminException("admin can not be deactivated");
        }
        isExisted.setActive(false);
    }

    @Override
    public void viewTransactionHistory(Account admin , String userName) {
        checkAdminException(admin);

        Account isExisted = getAccountByUsername(userName);
        if (isExisted == null){
            throw new AccountNotExistedException("account not existed");
        }

        for (Transaction transaction : isExisted.getTransactions()) {
            System.out.println(transaction);
        }
    }

    @Override
    public Boolean isAdmin(Account account) {
        return account.getAdmin();
    }

    @Override
    public void checkAdminException(Account admin) {
        if (!isAdmin(admin)){
            throw new NotAdminException("admin only is allowed to do that");
        }
    }

}
