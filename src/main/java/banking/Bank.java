package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<Integer, Account> accounts;
	private Integer time;

	Bank() {
		accounts = new HashMap<>();
		time = 0;
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

	public void passTime(int months) {
		for (int i = 0; i < months; i++) {
			for (Map.Entry<Integer, Account> entry : accounts.entrySet()) {
				Account account = entry.getValue();
				String accountType = account.getAccountType();
				double apr = account.getApr();
				double balance = account.getBalance();

				double monthlyInterest = (apr / 100 / 12) * balance;
				double minimumBalanceFee = 25;

				if (balance == 0) {
					accounts.remove(account.getId());
				} else if (balance < 100) {
					account.withdraw_money(minimumBalanceFee);
				} else if ((accountType.equals("savings")) || accountType.equals("checking")) {
					account.deposit_money(monthlyInterest);
				} else {
					for (int j = 0; j < 4; j++) {
						double depositAmount = account.getBalance() * monthlyInterest;
						depositById(account.getId(), depositAmount);
					}
				}
			}
			time += 1;
		}
	}

	public int getMonths() {
		return time;
	}
}