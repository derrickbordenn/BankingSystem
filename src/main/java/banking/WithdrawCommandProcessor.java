package banking;

public class WithdrawCommandProcessor extends CommandProcessor {
	public WithdrawCommandProcessor(Bank bank) {
		super(bank);
	}

	public void processWithdrawCommand(String[] commandParts) {
		int id = Integer.parseInt(commandParts[1]);
		double amount = Double.parseDouble(commandParts[2]);
		bank.withdrawById(id, amount);
	}
}
