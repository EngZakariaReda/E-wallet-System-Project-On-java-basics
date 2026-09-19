package Services.Impl;
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
        Boolean isAccountExisted = walletSystem.getAccounts().
                stream()
                .anyMatch(acc -> acc.getUserName().equals(account.getUserName()));

        if (isAccountExisted){
            return null ;
        }

        walletSystem.getAccounts().add(account);
        return account;
    }

    @Override
    public Account getAccountByUserNameAndPassword(Account account) {
        Optional<Account> existedAccount = walletSystem.getAccounts().
                stream()
                .filter(acc -> acc.getUserName().equals(account.getUserName())
                        && acc.getPassword().equals(account.getPassword()))
                .findFirst();

        if(existedAccount.isPresent()){
            return existedAccount.get();
        }else {
            return null ;
        }
    }

    @Override
    public Account depositToAccount(Account account) {
        System.out.println("enter the amount of money you want to deposit");
        double amount = scanner.nextDouble();

        if (amount <= 0){
            System.out.println("the amount you enter can not be deposited");
            return account ;
        }

        Double newBalance = account.getBalance() + amount ;
        account.setBalance(newBalance);
        System.out.println("operation success now newBalance is " + newBalance);
        return account ;
    }

    @Override
    public Account withdrawFromAccount(Account account) {
        System.out.println("enter the amount of money you want to withdraw");
        double amount = scanner.nextDouble();

        if (amount <= 0){
            System.out.println("the amount you enter must be greater than 0");
            return account ;
        }

        if (account.getBalance() < amount){
            System.out.println("Insufficient balance");
            return account ;
        }

        Double newBalance = account.getBalance() - amount ;
        account.setBalance(newBalance);
        System.out.println("operation success now newBalance is " + newBalance);
        return account ;
    }

    @Override
    public Account transferFromAccountToAnother(Account account) {
        System.out.println("pls enter userName you want send to him");
        String reciverUserName = scanner.next();

        if ((account.getUserName().equals(reciverUserName))){
            System.out.println("invalid operation");
            return account ;
        }

        Optional<Account> receiverAccount = walletSystem.getAccounts().
                stream().
                filter(acc -> acc.getUserName().equals(reciverUserName)).
                findFirst();

        if (!(receiverAccount.isPresent())){
            System.out.println("this receiver account not existed");
            return account ;
        }

        System.out.println("enter the amount of money you want to transfer");
        double amount = scanner.nextDouble();

        if (amount <= 0){
            System.out.println("the amount you enter must be greater than 0");
            return account ;
        }

        if (amount > account.getBalance()){
            System.out.println("Insufficient balance");
            return account ;
        }

        account.setBalance(account.getBalance() - amount);
        Account received = receiverAccount.get();
        received.setBalance(received.getBalance() + amount);
        System.out.println(walletSystem.getAccounts());
        System.out.println("amount transfered successfully");
        return account ;
    }

    @Override
    public Account changePasswordOfAccount(Account account) {
        System.out.println("pls enter old password");
        String oldPassword = scanner.next();

        if (!(account.getPassword().equals(oldPassword))){
            System.out.println("incorrect password");
            return account;
        }

        System.out.println("pls enter new password");
        String newPassword = scanner.next();
        account.setPassword(newPassword);
        System.out.println("password changed successfully");
        return account ;
    }

    @Override
    public void showBalanceOfAccount(Account account) {
        System.out.println("your balance is " + account.getBalance());
    }

    @Override
    public void showDetailsOfAccount(Account account) {
        System.out.println("Username: " + account.getUserName());
        System.out.println("password: " + "*".repeat(account.getPassword().length()));
        System.out.println("Phone: " + account.getPhoneNumber());
        System.out.println("Balance: " + account.getBalance());
        System.out.println("Age: " + account.getAge());
    }
}
