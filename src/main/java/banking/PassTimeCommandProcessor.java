package banking;

public class PassTimeCommandProcessor extends CommandProcessor {

	public PassTimeCommandProcessor(Bank bank) {
		super(bank);
	}

	public void processPassTimeCommand(String[] commandParts) {
		int months = Integer.parseInt(commandParts[1]);
		bank.passTime(months);
	}
}
