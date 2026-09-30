package Services.Impl;
import Enums.*;
import Exceptions.AccountNotExisted;
import Exceptions.SamePassword;
import Helper.InputHelper;
import Helper.Validator;
import Models.Account;
import Models.WalletSystem;
import Services.ApplicationService;

import java.util.Scanner;

public class WalletApplicationServiceImpl implements ApplicationService {

   private Scanner scanner = new Scanner(System.in);
   private AccountServiceImpl accountService = new AccountServiceImpl();
   private InputHelper inputHelper = new InputHelper(scanner);
   private Validator validator = new Validator(accountService);

    @Override
    public void start() {
        System.out.println("welcome to " + WalletSystem.walletName);

        Integer count = 0 ;
        while (true){
            System.out.println("pls choose -------->");
            System.out.println("1.login     2.signUp        3.exit");
            Integer choose = inputHelper.readInteger();
            Boolean isExit = false ;

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
                System.out.println("welcome to your account");
                mainProfile(newAccount);
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
                    "1.deposit   " +
                            "2.withdraw  " +
                            "3.Transfer   " +
                            "4.show balance    " +
                            "5.show details   " +
                            "6.Change Password   " +
                            "7.logout"
            );

            Boolean exitApp = false ;
            Integer chooseOperation = inputHelper.readInteger();
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
                    showBalanceDetails(account);
                    break;

                case 6 :
                    changePassword(account);
                    break;

                case 7 :
                    System.out.println("goodbye");
                    exitApp = true ;
                    break;

                default:
                    numberOfAttempts += 1;
                    System.out.println("pls choose valid number from 1 to 7");
                    break;
            }

            if (numberOfAttempts == 4){
                break;
            }

            if (exitApp){
                break;
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

        String receiverUserName = inputHelper.readString("please enter the userName of receiver account");
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
        } catch (AccountNotExisted e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void showBalance(Account account) {
        Double balance = getBalance(account);
        System.out.println("your current balance is " + balance);
    }

    private void showBalanceDetails(Account account) {
        try{
            accountService.showDetails(account);
        } catch (AccountNotExisted e) {
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
            } catch (SamePassword e) {
                System.out.println(e.getMessage());
            } catch (AccountNotExisted e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

}
