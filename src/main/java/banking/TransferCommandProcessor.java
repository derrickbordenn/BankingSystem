package banking;

public class TransferCommandProcessor extends CommandProcessor {
	public TransferCommandProcessor(Bank bank) {
		super(bank);
	}

	public void processTransferCommand(String[] commandParts) {
		double amount = Double.parseDouble(commandParts[3]);
		bank.transfer(commandParts[1], commandParts[2], amount);
	}
}
