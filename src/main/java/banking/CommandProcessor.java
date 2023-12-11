package banking;

public class CommandProcessor {
	Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String command) {
		String[] commandParts = command.split(" ");
		String commandType = commandParts[0].toLowerCase();
		if (commandType.equals("create")) {
			CreateCommandProcessor createCommandProcessor = new CreateCommandProcessor(bank);
			createCommandProcessor.processCreateCommand(commandParts);
		} else if (commandType.equals("deposit")) {
			DepositCommandProcessor depositCommandProcessor = new DepositCommandProcessor(bank);
			depositCommandProcessor.processDepositCommand(commandParts);
		}
	}
}