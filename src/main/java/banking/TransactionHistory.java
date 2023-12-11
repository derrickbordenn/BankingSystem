package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
	Bank bank;
	private List<String> output;

	public TransactionHistory(Bank bank) {
		this.output = new ArrayList<>();
		this.bank = bank;
	}

	public String getAccountStatus(Account account) {
		String id = account.getId();
		double apr = account.getApr();
		double balance = account.getBalance();
		String accountType = account.getAccountType();
		String accountTypeFormatted = Character.toUpperCase(accountType.charAt(0)) + accountType.substring(1);
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);
		String aprFormatted = decimalFormat.format(apr);
		String balanceFormatted = decimalFormat.format(balance);
		return String.format("%s %s %s %s", accountTypeFormatted, id, balanceFormatted, aprFormatted);
	}

	public List<String> getTransactionHistory(List<String> validCommands, List<String> invalidCommands) {
		for (String id : bank.getAccountsInOrder()) {
			if (bank.accountExistsByQuickID(id)) {
				Account account = bank.getAccountById(id);
				output.add(getAccountStatus(account));
				for (String command : validCommands) {
					String[] commandParts = command.split(" ");
					String commandType = commandParts[0].toLowerCase();
					if ((!commandType.equals("create") && !commandType.equals("pass"))
							&& (commandParts[1].equals(id) || commandParts[2].equals(id))) {
						output.add(command);
					}
				}
			}
		}
		output.addAll(invalidCommands);
		return new ArrayList<>(output);
	}
}