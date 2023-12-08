package banking;

public class WithdrawCommandValidator extends CommandValidator {
	int count;

	public WithdrawCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateWithdrawCommand(String[] commandParts) {
		if (commandParts.length != 3 || bank.withdrawalsThisMonth() != 0) {
			return false;
		}

		String Id = commandParts[1];
		String Amount = commandParts[2];

		if (validId(Id) && validAmount(Amount)) {
			int id = Integer.parseInt(Id);
			String accountType = bank.getAccountType(id);
			double amount = Double.parseDouble(Amount);
			if (bank.accountExistsByQuickID(id)) {
				if (accountType.equals("savings")) {
					return (amount <= 1000);
				} else if (accountType.equals("checking")) {
					return (amount <= 400);
				} else if (accountType.equals("cd")) {
					return (amount >= bank.getAccountById(id).getBalance() && bank.getMonths() >= 12);
				}
			}
		}
		return false;
	}
}