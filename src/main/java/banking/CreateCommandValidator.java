package banking;

public class CreateCommandValidator extends CommandValidator {
	public CreateCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateCreateCommand(String[] commandParts) {
		if (commandParts.length < 4) {
			return false;
		}

		String accountType = commandParts[1].toLowerCase();
		String id = commandParts[2];
		String apr = commandParts[3];

		if (bank.accountExistsByQuickID(id)) {
			return false;
		}

		if (validAccountType(accountType) && validId(id) && validApr(apr)) {
			if (accountType.equals("cd")) {
				if (commandParts.length != 5) {
					return false;
				}
				String CDInitialBalance = commandParts[4];
				return validInitialBalance(CDInitialBalance);
			} else if (commandParts.length == 4) {
				return true;
			}
		}
		return false;
	}
}
