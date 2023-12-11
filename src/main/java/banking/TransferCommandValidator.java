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
		String fromId = commandParts[1];
		String toID = commandParts[2];
		String Amount = commandParts[3];
		validateDeposit = new DepositCommandValidator(bank);
		validateWithdraw = new WithdrawCommandValidator(bank);
		if (validId(fromId) && validId(toID) && validAmount(Amount) && (!fromId.equals(toID))) {
			String depositString = "deposit " + toID + " " + Amount;
			String withdrawString = "withdraw " + fromId + " " + Amount;
			String[] depositCommand = depositString.split(" ");
			String[] withdrawCommand = withdrawString.split(" ");
			boolean validDeposit = validateDeposit.validateDepositCommand(depositCommand);
			boolean validWithdraw = validateWithdraw.validateWithdrawCommand(withdrawCommand);
			String fromAccountType = bank.getAccountType(fromId);
			if (bank.accountExistsByQuickID(fromId) && bank.accountExistsByQuickID(toID)
					&& (!fromAccountType.equals("cd"))) {
				return (validWithdraw && validDeposit);
			}
		}
		return false;
	}
}
