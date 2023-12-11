package banking;

public class DepositCommandProcessor extends CommandProcessor {
	public DepositCommandProcessor(Bank bank) {
		super(bank);
	}

	public void processDepositCommand(String[] commandParts) {
		int id = Integer.parseInt(commandParts[1]);
		double amount = Double.parseDouble(commandParts[2]);
		bank.depositById(id, amount);
	}
}
