package banking;

public class CreateCommandProcessor extends CommandProcessor {
	public CreateCommandProcessor(Bank bank) {
		super(bank);
	}

	public void processCreateCommand(String[] commandParts) {
		String accountType = commandParts[1];
		String id = commandParts[2];
		double apr = Double.parseDouble(commandParts[3]);
		if (commandParts.length < 5) {
			if (accountType.equals("savings")) {
				bank.addSavingsAccount(id, apr);
			} else {
				bank.addCheckingAccount(id, apr);
			}
		} else {
			double balance = Double.parseDouble(commandParts[4]);
			bank.addCDAccount(id, apr, balance);
		}

	}
}