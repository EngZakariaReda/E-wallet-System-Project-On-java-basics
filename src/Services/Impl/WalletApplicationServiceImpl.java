package Services.Impl;
import Models.Account;
import Models.WalletSystem;
import Services.ApplicationService;

import java.util.Scanner;

public class WalletApplicationServiceImpl implements ApplicationService {

   private Scanner scanner = new Scanner(System.in);
   private AccountServiceImpl accountService = new AccountServiceImpl();


    @Override
    public void start() {
        System.out.println("welcome to " + WalletSystem.walletName);

        Integer count = 0 ;
        while (true){
            System.out.println("pls choose -------->");
            System.out.println("1.login     2.signUp        3.exit");
            Integer choose = scanner.nextInt();
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
        System.out.print("Enter username: ");
        String userName = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.next();

        System.out.print("Enter age: ");
        Float age = scanner.nextFloat();

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
        System.out.print("Enter username: ");
        String userName = scanner.next();

        System.out.print("Enter password: ");
        String password = scanner.next();

        Account newAccount = new Account(userName , password);
        newAccount = accountService.getAccountByUserNameAndPassword(newAccount);

        if(newAccount == null){
            System.out.println("email or password is not correct");
        }else {
            System.out.println("welcome to your account");
            mainProfile(newAccount);
        }
    }

    private void mainProfile(Account account) {

        Account currentAccount = account ;
        Integer numberOfAttempts = 0 ;
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
            Integer chooseOperation = scanner.nextInt();
            switch (chooseOperation){
                case 1 :
                    currentAccount = accountService.depositToAccount(currentAccount);
                    break;

                case 2 :
                    currentAccount = accountService.withdrawFromAccount(currentAccount);
                    break;

                case 3 :
                    accountService.transferFromAccountToAnother(currentAccount);
                    break;

                case 4 :
                    accountService.showBalanceOfAccount(currentAccount);
                    break;

                case 5 :
                    accountService.showDetailsOfAccount(currentAccount);
                    break;

                case 6 :
                    currentAccount = accountService.changePasswordOfAccount(currentAccount);
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

}
