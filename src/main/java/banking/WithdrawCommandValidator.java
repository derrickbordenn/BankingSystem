package banking;

public class WithdrawCommandValidator extends CommandValidator {

	public WithdrawCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateWithdrawCommand(String[] commandParts) {
		if (commandParts[2].equals("0")) {
			return true;
		} else {
			return false;
		}
	}
}
