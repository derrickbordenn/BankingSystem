package banking;

public class CommandValidator {
	Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] commandParts = command.split(" ");

		if (commandParts.length == 0) {
			return false;
		}

		String commandType = commandParts[0].toLowerCase();

		if (commandType.equals("create")) {
			CreateCommandValidator createCommandValidator = new CreateCommandValidator(bank);
			return createCommandValidator.validateCreateCommand(commandParts);
		} else if (commandType.equals("deposit")) {
			DepositCommandValidator depositCommandValidator = new DepositCommandValidator(bank);
			return depositCommandValidator.validateDepositCommand(commandParts);
		} else if (commandType.equals("withdraw")) {
			WithdrawCommandValidator withdrawCommandValidator = new WithdrawCommandValidator(bank);
			return withdrawCommandValidator.validateWithdrawCommand(commandParts);
		} else {
			return false;
		}
	}

	public boolean validAccountType(String accountType) {
		return accountType.equals("checking") || accountType.equals("savings") || accountType.equals("cd");
	}

	public boolean validId(String Id) {
		if (Id.length() != 8) {
			return false;
		}
		try {
			int id = Integer.parseInt(Id);
			if (id < 0 || id > 99999999) {
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean validApr(String apr) {
		try {
			double Apr = Double.parseDouble(apr);
			if (Apr < 0 || Apr > 10) {
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean validInitalBalance(String balance) {
		try {
			double Balance = Double.parseDouble(balance);
			if (Balance < 1000 || Balance > 10000) {
				return false;
			}
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
