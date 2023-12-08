package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<Integer, Account> accounts;
	private Integer months;

	Bank() {
		accounts = new HashMap<>();
		months = 0;
	}

	public void addAccount(Account account) {
		accounts.put(account.getId(), account);
	}

	public Account getAccountById(int id) {
		return accounts.get(id);
	}

	public String getAccountType(int id) {
		if (getAccountById(id) != null) {
			return getAccountById(id).getAccountType();
		} else {
			return null;
		}
	}

	public int getAccountCount() {
		return accounts.size();
	}

	public void depositById(int id, double amount) {
		Account account = getAccountById(id);
		account.deposit_money(amount);
	}

	public void withdrawById(int id, double amount) {
		Account account = getAccountById(id);
		account.withdraw_money(amount);
	}

	public boolean accountExistsByQuickID(int id) {
		return getAccountById(id) != null;
	}

	public void passTime(int time) {
		months += time;
	}

	public int getMonths() {
		return months;
	}

}