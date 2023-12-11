package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<Integer, Account> accounts;
	private int time;
	private int withdrawals;

	Bank() {
		accounts = new HashMap<>();
		time = 0;
		withdrawals = 0;
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
		countWithdraw();
	}

	public boolean accountExistsByQuickID(int id) {
		return getAccountById(id) != null;
	}

	public void passTime(int months) {
		resetWithdrawals();
		for (int i = 0; i < months; i++) {
			time += 1;
			for (Map.Entry<Integer, Account> entry : accounts.entrySet()) {
				Account account = entry.getValue();
				String accountType = account.getAccountType();
				double apr = account.getApr();
				double balance = account.getBalance();
				double minimumBalanceFee = 25;

				if (balance == 0) {
					accounts.remove(account.getId());
				} else if (balance < 100) {
					account.withdraw_money(minimumBalanceFee);
				} else if ((accountType.equals("savings")) || accountType.equals("checking")) {
					account.deposit_money(calculatedMonthlyInterest(apr, balance));
				} else {
					for (int j = 0; j < 4; j++) {
						account.deposit_money(calculatedMonthlyInterest(apr, balance));
					}
				}
			}
		}
	}

	public int getMonths() {
		return time;
	}

	public double calculatedMonthlyInterest(double apr, double balance) {
		return (apr / 100 / 12) * balance;
	}

	public void countWithdraw() {
		withdrawals += 1;
	}

	public void resetWithdrawals() {
		withdrawals = 0;
	}

	public int withdrawalsThisMonth() {
		return withdrawals;
	}

	public void transfer(int fromId, int toId, double amount) {
		Account fromAccount = getAccountById(fromId);
		Account toAccount = getAccountById(toId);
		if (fromAccount.getBalance() < amount) {
			amount = fromAccount.getBalance();
		}
		withdrawById(fromId, amount);
		toAccount.deposit_money((amount));
	}
}