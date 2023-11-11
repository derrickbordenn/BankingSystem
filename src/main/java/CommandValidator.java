public class CommandValidator {

	public boolean validate(String command) {
		String[] commandParts = command.split(" ");

		if (commandParts.length == 0) {
			return false;
		}

		String commandType = commandParts[0].toLowerCase();

		if (commandType.equals("create")) {
			CreateCommandValidator createCommandValidator = new CreateCommandValidator();
			return createCommandValidator.validateCreateCommand(commandParts);
		} else if (commandType.equals("deposit")) {
			DepositCommandValidator depositCommandValidator = new DepositCommandValidator();
			return depositCommandValidator.validateDepositCommand(commandParts);
		} else {
			return false;
		}
	}
}
