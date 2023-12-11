package banking;

import java.util.List;

public class MasterControl {
	private CommandValidator commandValidator;
	private CommandProcessor commandProcessor;
	private CommandStore commandStore;
	private TransactionHistory transactionHistory;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			CommandStore commandStore, TransactionHistory transactionHistory) {
		this.commandValidator = commandValidator;
		this.commandProcessor = commandProcessor;
		this.commandStore = commandStore;
		this.transactionHistory = transactionHistory;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (commandValidator.validate(command)) {
				commandProcessor.process(command);
				commandStore.addValidCommand(command);
			} else {
				commandStore.addInvalidCommand(command);
			}
		}
		return transactionHistory.getTransactionHistory(commandStore.getAllValidCommands(),
				commandStore.getAllInvalidCommands());
	}

}
