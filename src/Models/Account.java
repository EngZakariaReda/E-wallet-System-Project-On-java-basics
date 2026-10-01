package Models;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String userName;
    private String password;
    private String phoneNumber;
    private Double balance;
    private Float age;
    private Boolean isAdmin;
    private Boolean isActive;
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String userName, String password, String phoneNumber, Float age) {
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.balance = 0.0;
        this.age = age;
        this.isAdmin = false;
        this.isActive = true;
    }

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public Float getAge() {
        return age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAge(Float age) {
        this.age = age;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Account{" + "userName='" + userName + '\'' +
                ", password='" +  "*".repeat(password.length()) + '\'' + ", phoneNumber='" +
                phoneNumber + '\'' + ", balance=" + balance + ", age=" + age + '}' + "active" + isActive;
    }
}
