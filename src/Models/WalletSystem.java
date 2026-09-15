package Models;
import java.util.ArrayList;
import java.util.List;

public class WalletSystem {
    public final static String walletName = "Mansour Cash";
    List<Account> accounts = new ArrayList();

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
