public class CommandProcessor {
	private Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void Process(String command) {
		String[] commandParts = command.split(" ");
		String commandType = commandParts[0].toLowerCase();
		String accountType = commandParts[1].toLowerCase();
		if (commandType.equals("create")) {
			int id = Integer.parseInt(commandParts[2]);
			double apr = Double.parseDouble(commandParts[3]);
			if (commandParts.length < 5) {
				if (accountType.equals("savings")) {
					bank.addAccount(new SavingsAccount(id, apr));
				} else {
					bank.addAccount(new CheckingAccount(id, apr));
				}
			}
		}
	}
}