import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts;

    Bank() {
        accounts = new HashMap<>();
    }

    public void addAccount(Account account) {
        accounts.put(account.getId(), account);
    }

    public Account getAccountById(String id) {
        return accounts.get(id);
    }

    public int getAccountCount() {
        return accounts.size();
    }

    public void depositById(String id, double amount) {
        Account account = getAccountById(id);
        account.deposit_money(amount);
    }

    public void withdrawById(String id, double amount) {
        Account account = getAccountById(id);
        account.withdraw_money(amount);
    }
}