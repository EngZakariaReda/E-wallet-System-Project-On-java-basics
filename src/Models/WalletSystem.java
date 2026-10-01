package Models;
import java.util.ArrayList;
import java.util.List;

public class WalletSystem {
    public final static String walletName = "Mansour Cash";
    List<Account> accounts = new ArrayList();

    public WalletSystem() {
        createAdmin();

    }
    private void createAdmin() {
        Account admin = new Account(
                "IAM",
                "IAM123",
                "01000000000",
                30.0f
        );

        admin.setAdmin(true);
        accounts.add(admin);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
