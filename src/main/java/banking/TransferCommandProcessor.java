package banking;

public class TransferCommandProcessor extends CommandProcessor {
	public TransferCommandProcessor(Bank bank) {
		super(bank);
	}

	public void processTransferCommand(String[] commandParts) {
		int fromId = Integer.parseInt(commandParts[1]);
		int toId = Integer.parseInt(commandParts[2]);
		double amount = Double.parseDouble(commandParts[3]);
		bank.transfer(fromId, toId, amount);
	}
}
