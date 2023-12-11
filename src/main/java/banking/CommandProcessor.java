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
		} else if (commandType.equals("withdraw")) {
			WithdrawCommandProcessor withdrawCommandProcessor = new WithdrawCommandProcessor(bank);
			withdrawCommandProcessor.processWithdrawCommand(commandParts);
		} else if (commandType.equals("pass")) {
			PassTimeCommandProcessor passTimeCommandProcessor = new PassTimeCommandProcessor(bank);
			passTimeCommandProcessor.processPassTimeCommand(commandParts);
		} else if (commandType.equals("transfer")) {
			TransferCommandProcessor transferCommandProcessor = new TransferCommandProcessor(bank);
			transferCommandProcessor.processTransferCommand(commandParts);
		}
	}
}