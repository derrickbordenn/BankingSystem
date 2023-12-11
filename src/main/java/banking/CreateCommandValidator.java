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
		String Apr = commandParts[3];

		if (validAccountType(accountType) && validId(id) && validApr(Apr)) {
			if (bank.accountExistsByQuickID(id)) {
				return false;
			} else if (accountType.equals("cd")) {
				if (commandParts.length != 5) {
					return false;
				}
				String CDInitialBalance = commandParts[4];
				return validInitialBalance(CDInitialBalance);
			} else {
				return commandParts.length == 4;
			}
		}
		return false;
	}

}
