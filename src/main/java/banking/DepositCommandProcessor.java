package banking;

public class DepositCommandProcessor extends CommandProcessor {
	public DepositCommandProcessor(Bank bank) {
		super(bank);
	}

	public void processDepositCommand(String[] commandParts) {
		double amount = Double.parseDouble(commandParts[2]);
		bank.depositById(commandParts[1], amount);
	}
}
