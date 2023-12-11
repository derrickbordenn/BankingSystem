package banking;

public class WithdrawCommandProcessor extends CommandProcessor {
	public WithdrawCommandProcessor(Bank bank) {
		super(bank);
	}

	public void processWithdrawCommand(String[] commandParts) {
		double amount = Double.parseDouble(commandParts[2]);
		bank.withdrawById(commandParts[1], amount);
	}
}
