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
		} else {
			return false;
		}
	}

	public boolean validAccountType(String accountType) {
		return accountType.equals("checking") || accountType.equals("savings") || accountType.equals("cd");
	}

	public boolean validId(String Id) {
		for (char c : Id.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return (Id.length() == 8);
	}

	public boolean validApr(String apr) {
		int periodCount = 0;
		for (char c : apr.toCharArray()) {
			if (!Character.isDigit(c) && c != '.') {
				return false;
			}
			if (c == '.') {
				periodCount++;
			}
		}
		if (periodCount > 1) {
			return false;
		} else {
			float aprToFloat = Float.parseFloat(apr);
			return aprToFloat >= 0 && aprToFloat <= 10;
		}
	}

	public boolean validInitalBalance(String balance) {
		int periodCount = 0;
		for (char c : balance.toCharArray()) {
			if (!Character.isDigit(c) && c != '.') {
				return false;
			}
			if (c == '.') {
				periodCount++;
			}
		}
		if (periodCount > 1) {
			return false;
		}
		float balanceToFloat = Float.parseFloat(balance);
		return (balanceToFloat >= 1000 && balanceToFloat <= 10000);
	}

	public boolean validDeposit(String amount) {
		int periodCount = 0;
		for (char c : amount.toCharArray()) {
			if (!Character.isDigit(c) && c != '.') {
				return false;
			}
			if (c == '.') {
				periodCount++;
			}
		}
		if (periodCount > 1) {
			return false;
		}
		float amountToFloat = Float.parseFloat(amount);
		return (amountToFloat >= 0);
	}
}
