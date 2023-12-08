package banking;

public class DepositCommandValidator extends CommandValidator {
	public DepositCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateDepositCommand(String[] commandParts) {
		if (commandParts.length != 3) {
			return false;
		}
		String Id = commandParts[1];
		String amount = commandParts[2];
		int id = Integer.parseInt(Id);
		double depositAmount = Double.parseDouble(amount);
		String accountType = bank.getAccountType(id);
		if (accountType.equals("savings")) {
			return (depositAmount <= 2500 && depositAmount >= 0 && validId(Id));
		} else if (accountType.equals("checking")) {
			return (depositAmount <= 1000 && depositAmount >= 0 && validId(Id));
		} else {
			return false;
		}
	}
}
