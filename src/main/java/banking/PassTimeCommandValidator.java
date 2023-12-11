package banking;

public class PassTimeCommandValidator extends CommandValidator {
	public PassTimeCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validatePassTimeCommand(String[] commandParts) {
		if (commandParts.length != 2) {
			return false;
		}
		String month = commandParts[1];

		return validMonth(month);
	}

	public boolean validMonth(String month) {
		try {
			int Month = Integer.parseInt(month);
			return Month >= 1 && Month <= 60;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
