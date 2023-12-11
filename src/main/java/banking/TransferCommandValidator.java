package banking;

public class TransferCommandValidator extends CommandValidator {
	DepositCommandValidator validateDeposit;
	WithdrawCommandValidator validateWithdraw;

	public TransferCommandValidator(Bank bank) {
		super(bank);
	}

	public boolean validateTransferCommand(String[] commandParts) {
		if (commandParts.length != 4) {
			return false;
		}
		String fromID = commandParts[1];
		String toID = commandParts[2];
		String Amount = commandParts[3];
		validateDeposit = new DepositCommandValidator(bank);
		validateWithdraw = new WithdrawCommandValidator(bank);
		if (validId(fromID) && validId(toID) && validAmount(Amount) && (!fromID.equals(toID))) {
			int fromId = Integer.parseInt(fromID);
			int toId = Integer.parseInt(toID);
			String depositString = "deposit " + toID + " " + Amount;
			String withdrawString = "withdraw " + fromID + " " + Amount;
			String[] depositCommand = depositString.split(" ");
			String[] withdrawCommand = withdrawString.split(" ");
			boolean validDeposit = validateDeposit.validateDepositCommand(depositCommand);
			boolean validWithdraw = validateWithdraw.validateWithdrawCommand(withdrawCommand);
			String fromAccountType = bank.getAccountType(fromId);
			if (bank.accountExistsByQuickID(fromId) && bank.accountExistsByQuickID(toId)
					&& (!fromAccountType.equals("cd"))) {
				return (validWithdraw && validDeposit);
			}
		}
		return false;
	}
}
