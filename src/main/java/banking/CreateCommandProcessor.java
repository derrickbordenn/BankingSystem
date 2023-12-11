package banking;

public class CreateCommandProcessor extends CommandProcessor {
	public CreateCommandProcessor(Bank bank) {
		super(bank);
	}

	public void processCreateCommand(String[] commandParts) {
		String accountType = commandParts[1];
		int id = Integer.parseInt(commandParts[2]);
		double apr = Double.parseDouble(commandParts[3]);
		if (commandParts.length < 5) {
			if (accountType.equals("savings")) {
				bank.addAccount(new SavingsAccount(id, apr));
			} else {
				bank.addAccount(new CheckingAccount(id, apr));
			}
		} else {
			double balance = Double.parseDouble(commandParts[4]);
			bank.addAccount(new CDAccount(id, apr, balance));
		}

	}
}