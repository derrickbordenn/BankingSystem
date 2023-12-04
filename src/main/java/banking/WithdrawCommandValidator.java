package banking;

public class WithdrawCommandValidator extends CommandValidator {

	public WithdrawCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateWithdrawCommand(String[] commandParts) {
		int id = Integer.parseInt(commandParts[1]);
		double amount = Double.parseDouble(commandParts[2]);
		String accountType = bank.getAccountType(id);
		if (accountType.equals("savings")) {
			return (amount >= 0 && amount <= 1000);
		} else {
			return false;
		}
	}
}
