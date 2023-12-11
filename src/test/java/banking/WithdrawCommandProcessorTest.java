package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawCommandProcessorTest {
	Bank bank;
	CommandProcessor commandProcessor;
	Account savings;
	Account checking;
	Account cd;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		savings = new SavingsAccount(1234578, 2.4);
		checking = new CheckingAccount(12345679, 2.4);
		cd = new CDAccount(12345678, 2.1, 2000);
	}

	@Test
	public void withdrawing_from_savings_subtracts_amount_from_balance() {
		bank.addAccount(savings);
		savings.deposit_money(250);
		command
		assertEquals();
	}

	@Test
	public void withdrawing_money_twice_from_same_account_subtracts_withdrawal_amount_twice_from_balance() {
		savings.deposit_money(DEPOSIT_AMOUNT);
		savings.withdraw_money(WITHDRAWAL_AMOUNT);
		savings.withdraw_money(WITHDRAWAL_AMOUNT);

		double actual = savings.getBalance();

		assertEquals(DEPOSIT_AMOUNT - WITHDRAWAL_AMOUNT * 2, actual);
	}

	@Test
	public void withdrawing_larger_amount_than_account_balance_does_not_result_in_negative_balance() {
		savings.deposit_money(DEPOSIT_AMOUNT);
		savings.withdraw_money(DEPOSIT_AMOUNT + 1);
		double actual = savings.getBalance();

		assertEquals(0, actual);
	}
}
