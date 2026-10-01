package Services.Impl;
import Enums.*;
import Exceptions.*;
import Helper.InputHelper;
import Helper.Validator;
import Models.Account;
import Models.WalletSystem;
import Services.ApplicationService;

import java.util.Scanner;

public class WalletApplicationServiceImpl implements ApplicationService {

   private final Scanner scanner = new Scanner(System.in);
   private final AccountServiceImpl accountService = new AccountServiceImpl();
   private final InputHelper inputHelper = new InputHelper(scanner);
   private final Validator validator = new Validator(accountService);

    @Override
    public void start() {
        System.out.println("welcome to " + WalletSystem.walletName);

        int count = 0 ;
        while (true){
            System.out.println("pls choose -------->");
            System.out.println("1.login     2.signUp        3.exit");
            int choose = inputHelper.readInteger();
            boolean isExit = false ;

            switch (choose){
                case 1 :
                    logIn();
                    break;

                case 2 :
                    signUp();
                    break;

                case 3 :
                    System.out.println("exit feature");
                    isExit = true ;
                    break;

                default:
                    count += 1;
                    String message = count == 4
                            ? "contact with the admin"
                            : "pls choose valid number 1 or 2 or 3";
                    System.out.println(message);
                    break;
            }

            if (isExit){
                break;
            }

            if (count == 4){
                break;
            }
        }
    }

    private void signUp(){
        String userName;

        while (true) {
            userName = inputHelper.readString("Enter username: ");
            UsernameValidationStatus status =
                    validator.isValidUsername(userName);

            if (status == UsernameValidationStatus.VALID) {
                break;
            }

            System.out.println(status.getMessage());
        }

        String password;

        while (true) {
            password = inputHelper.readString("Enter password: ");

            PasswordValidationStatus status =
                    validator.isValidPassword(password);

            if (status == PasswordValidationStatus.VALID) {
                break;
            }

            System.out.println(status.getMessage());
        }

        Float age;

        while (true) {
            age = inputHelper.readFloat("Enter age: ");

            if (age >= 18) {
                break;
            }

            System.out.println("Age must be at least 18.");
        }

        String phoneNumber;

        while (true) {
            phoneNumber = inputHelper.readString("Enter phone number: ");

            PhoneValidationStatus status = validator.isValidPhone(phoneNumber);

            if (status == PhoneValidationStatus.VALID) {
                break;
            }

            System.out.println(status.getMessage());
        }

        Account newAccount = new Account(userName , password ,phoneNumber ,age);
        newAccount = accountService.createAccount(newAccount);

        if(newAccount == null){
            System.out.println("this account already existed");
        }else {
            System.out.println("account created successfully");
            mainProfile(newAccount);
        }
    }

    private void logIn(){
        String userName = null ;
        String password = null ;
        int invalidAttempts = 0 ;

        while (invalidAttempts < 5) {

            while (invalidAttempts < 5){
                userName = inputHelper.readString("Enter username: ");

                if (userName == null || userName.isBlank()) {
                    System.out.println("Username cannot be empty.");
                    invalidAttempts++ ;
                    continue;
                }

                break;
            }

            while (invalidAttempts < 5) {
                password = inputHelper.readString("Enter password: ");

                if (password == null || password.isBlank()) {
                    System.out.println("Password cannot be empty.");
                    invalidAttempts++;
                    continue;
                }
                break;
            }

            if (!(invalidAttempts < 5)){
                break;
            }

            Account newAccount = new Account(userName , password);
            newAccount = accountService.getAccountByUserNameAndPassword(newAccount);

            if(newAccount == null){
                System.out.println("userName or password is not correct");
                invalidAttempts++;
            }else {
                if (newAccount.getActive()){
                    System.out.println("welcome to your account");
                    mainProfile(newAccount);
                }else {
                    System.out.println("your account is deactivated, please contact admin");
                }
                return;
            }
        }
        System.out.println("Too many failed attempts. Please try again later.");
    }

    private void mainProfile(Account account) {
        int numberOfAttempts = 0 ;
        while (true){
            System.out.println("pls choose -------->");
            System.out.println(
                    "1.Deposit   " +
                            "2.Withdraw  " +
                            "3.Transfer   " +
                            "4.Show Balance    " +
                            "5.Show Details   " +
                            "6.Change Password   " +
                            "7.Logout"
            );
            if (accountService.isAdmin(account)) {
                System.out.println(
                        "-------- ADMIN OPERATIONS --------\n" +
                                "8.View All Accounts " +
                                "9.View Account " +
                                "10.Delete Account " +
                                "11.Activate Account " +
                                "12.Deactivate Account " +
                                "13.View Transaction History Of Account"
                );
            }

            boolean exitApp = false ;
            int chooseOperation = inputHelper.readInteger();
            switch (chooseOperation){
                case 1 :
                    deposit(account);
                    break;

                case 2 :
                    withdraw(account);
                    break;

                case 3 :
                    transfer(account);
                    break;

                case 4 :
                    showBalance(account);
                    break;

                case 5 :
                    showDetails(account);
                    break;

                case 6 :
                    changePassword(account);
                    break;

                case 7:
                    System.out.println("goodbye");
                    return;

                case 8:
                    if (accountService.isAdmin(account)) {
                        accountService.viewAllAccounts(account);
                    }else {
                        System.out.println("Please choose a valid number.");
                        numberOfAttempts++;
                    }
                    break;

                case 9:
                    if (accountService.isAdmin(account)) {
                        viewAccount(account);
                    }else {
                        System.out.println("Please choose a valid number.");
                        numberOfAttempts++;
                    }
                    break;

                case 10:
                    if (accountService.isAdmin(account)) {
                        deleteAccount(account);
                    }else {
                        System.out.println("Please choose a valid number.");
                        numberOfAttempts++;
                    }
                    break;

                case 11:
                    if (accountService.isAdmin(account)) {
                        activateAccount(account);
                    }else {
                        System.out.println("Please choose a valid number.");
                        numberOfAttempts++;
                    }
                    break;

                case 12:
                    if (accountService.isAdmin(account)) {
                        deactivateAccount(account);
                    }else {
                        System.out.println("Please choose a valid number.");
                        numberOfAttempts++;
                    }
                    break;

                case 13:
                    if (accountService.isAdmin(account)) {
                        viewTransactionHistory(account);
                    }else {
                        System.out.println("Please choose a valid number.");
                        numberOfAttempts++;
                    }
                    break;

                default:
                    numberOfAttempts++;
                    System.out.println("Please choose a valid number.");
                    break;

            }
            if (numberOfAttempts >= 4) {
                System.out.println("Too many invalid attempts.");
                return;
            }
        }
    }

    private void deposit(Account account) {
        double amount = inputHelper.readDouble("Please enter the amount: ");

        DepositStatus depositStatus = accountService.deposit(account, amount);

        if (depositStatus == DepositStatus.SUCCESS) {
            Double  newAmountValue = getBalance(account) ;

            System.out.println(depositStatus.getMessage()
             + "the new balance is " + newAmountValue);
            return;
        }

        System.out.println(depositStatus.getMessage());
    }

    private void withdraw(Account account) {
        double amount = inputHelper.readDouble("Please enter the amount: ");

        WithdrawStatus withdrawStatus = accountService.withdraw(account, amount);

        if (withdrawStatus == WithdrawStatus.SUCCESS) {
            Double  newAmountValue = getBalance(account) ;

            System.out.println(withdrawStatus.getMessage()
                    + "the new balance is " + newAmountValue);
            return;
        }

        System.out.println(withdrawStatus.getMessage());
    }

    private void transfer(Account account) {
        TransferStatus transferStatus ;
        String receiverUserName = null ;

        while (true) {
            receiverUserName = inputHelper.readString("please enter the userName of receiver account");

            if (receiverUserName == null || receiverUserName.isBlank()) {
                System.out.println("please enter a valid name");
                continue;
            }
            break;
        }

        Account recieverAccount = accountService.getAccountByUsername(receiverUserName);

        if(recieverAccount == null){
            System.out.println(TransferStatus.RECEIVER_ACCOUNT_NOT_EXIST.getMessage());;
            return;
        }

        double amount = inputHelper.readDouble("Please enter the amount: ");

        transferStatus = accountService.transfer(account, recieverAccount , amount);

        if (transferStatus == TransferStatus.SUCCESS) {
            Double  newAmountValue = getBalance(account) ;

            System.out.println(transferStatus.getMessage()
                    + "the new balance is " + newAmountValue);
            return;
        }

        System.out.println(transferStatus.getMessage());
    }

    private Double getBalance(Account account) {
        try{
            return accountService.showBalance(account);
        } catch (AccountNotExistedException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void showBalance(Account account) {
        Double balance = getBalance(account);
        if (balance == null){
            return;
        }
        System.out.println("your current balance is " + balance);
    }

    private void showDetails(Account account) {
        try{
            accountService.showDetails(account);
        } catch (AccountNotExistedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void changePassword(Account account) {
        String oldPassword = inputHelper.readString("Please enter old password: ");

        if (!account.getPassword().equals(oldPassword)) {
            System.out.println("Incorrect password");
            return;
        }

        while (true) {
            String newPassword = inputHelper.readString("Enter new password: ");
            PasswordValidationStatus status = validator.isValidPassword(newPassword);

            if (status != PasswordValidationStatus.VALID) {
                System.out.println(status.getMessage());
                continue;
            }

            try {
                accountService.changePassword(account, oldPassword, newPassword);
                System.out.println("Password changed successfully");
                return;
            } catch (SamePasswordException | InvalidPasswordException e) {
                System.out.println(e.getMessage());
            } catch (AccountNotExistedException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    private void deactivateAccount(Account account) {
        String userName = inputHelper.readUsername();
        try {
            accountService.deactivateAccount(account, userName);
            System.out.println("account deactivated successfully");
        } catch (AccountNotExistedException | NotAdminException | InvalidActionOnAdminException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteAccount(Account account) {
        String userName = inputHelper.readUsername();
        try {
            accountService.deleteAccount(account, userName);
            System.out.println("account deleted successfully");
        } catch (AccountNotExistedException | NotAdminException | InvalidActionOnAdminException e) {
            System.out.println(e.getMessage());
        }
    }

    private void activateAccount(Account account) {
        String userName = inputHelper.readUsername();
        try {
            accountService.activateAccount(account, userName);
            System.out.println("account activated successfully");
        } catch (AccountNotExistedException | NotAdminException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewTransactionHistory(Account account) {
        String userName = inputHelper.readUsername();
        try {
            accountService.viewTransactionHistory(account, userName);
        } catch (AccountNotExistedException | NotAdminException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewAccount(Account account) {
        String userName = inputHelper.readUsername();
        try {
            accountService.viewAccount(account, userName);
        } catch (AccountNotExistedException | NotAdminException e) {
            System.out.println(e.getMessage());
        }
    }
}
