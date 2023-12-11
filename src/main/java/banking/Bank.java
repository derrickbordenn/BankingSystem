package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
	private Map<String, Account> accounts;
	private List<String> accountsInOrder;
	private int time;
	private int withdrawals;

	Bank() {
		accounts = new HashMap<>();
		time = 0;
		withdrawals = 0;
		accountsInOrder = new ArrayList<>();
	}

	public void addSavingsAccount(String id, double apr) {
		Account account = new SavingsAccount(id, apr);
		accounts.put(id, account);
		accountsInOrder.add(id);
	}

	public void addCheckingAccount(String id, double apr) {
		Account account = new CheckingAccount(id, apr);
		accounts.put(id, account);
		accountsInOrder.add(id);
	}

	public void addCDAccount(String id, double apr, double balance) {
		Account account = new CDAccount(id, apr, balance);
		accounts.put(id, account);
		accountsInOrder.add(id);
	}

	public Account getAccountById(String id) {
		return accounts.get(id);
	}

	public String getAccountType(String id) {
		if (getAccountById(id) != null) {
			return getAccountById(id).getAccountType();
		} else {
			return null;
		}
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
		countWithdraw();
	}

	public boolean accountExistsByQuickID(String id) {
		return getAccountById(id) != null;
	}

	public void passTime(int months) {
		resetWithdrawals();
		List<String> accountsToRemove = new ArrayList<>();
		for (int i = 0; i < months; i++) {
			time += 1;
			for (Map.Entry<String, Account> entry : accounts.entrySet()) {
				Account account = entry.getValue();
				String accountType = account.getAccountType();
				String id = account.getId();
				double apr = account.getApr();
				double balance = account.getBalance();
				double minimumBalanceFee = 25;

				if (balance == 0) {
					accountsToRemove.add(id);
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
		for (String ID : accountsToRemove) {
			accounts.remove(ID);
			accountsInOrder.remove(ID);
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

	public void transfer(String fromId, String toId, double amount) {
		Account fromAccount = getAccountById(fromId);
		Account toAccount = getAccountById(toId);
		if (fromAccount.getBalance() < amount) {
			amount = fromAccount.getBalance();
		}
		withdrawById(fromId, amount);
		toAccount.deposit_money((amount));
	}

	public List<String> getAccountsInOrder() {
		return accountsInOrder;
	}
}