package Models;

public class Account {
    private String userName;
    private String password;
    private String phoneNumber;
    private Double balance;
    private Float age;

    public Account(String userName, String password, String phoneNumber, Float age) {
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.balance = 0.0;
        this.age = age;
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

    @Override
    public String toString() {
        return "Account{" + "userName='" + userName + '\'' +
                ", password='" + password + '\'' + ", phoneNumber='" +
                phoneNumber + '\'' + ", balance=" + balance + ", age=" + age + '}';
    }
}
