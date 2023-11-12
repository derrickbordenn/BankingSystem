public class DepositCommandValidator extends CommandValidator {
	public boolean validateDepositCommand(String[] commandParts) {
		if (commandParts.length != 3) {
			return false;
		}

		String Id = commandParts[1];
		String amount = commandParts[2];

		return (validId(Id) && validDeposit(amount));
	}
}
