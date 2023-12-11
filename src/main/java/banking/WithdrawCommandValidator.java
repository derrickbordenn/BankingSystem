package banking;

public class WithdrawCommandValidator extends CommandValidator {

	public WithdrawCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateWithdrawCommand(String[] commandParts) {
		if (commandParts.length != 3) {
			return false;
		}

		String id = commandParts[1];
		String Amount = commandParts[2];

		if (validId(id) && validAmount(Amount)) {
			String accountType = bank.getAccountType(id);
			double amount = Double.parseDouble(Amount);
			if (bank.accountExistsByQuickID(id)) {
				if (accountType.equals("savings") && (bank.withdrawalsThisMonth() == 0)) {
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