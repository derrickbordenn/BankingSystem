package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawCommandProcessorTest {
	Bank bank;
	CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void withdrawing_from_savings_subtracts_amount_from_balance() {
		bank.addSavingsAccount("12345678", 2.1);
		bank.depositById("12345678", 250);
		commandProcessor.process("withdraw 12345678 150");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(100, actual);
	}

	@Test
	void withdraw_zero_from_savings() {
		bank.addSavingsAccount("12345678", 2.1);
		bank.depositById("12345678", 200);
		commandProcessor.process("withdraw 12345678 0");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(200, actual);
	}

	@Test
	void withdraw_max_from_savings() {
		bank.addSavingsAccount("12345678", 2.1);
		bank.depositById("12345678", 1500);
		commandProcessor.process("withdraw 12345678 1000");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(500, actual);
	}

	@Test
	void withdraw_balance_from_savings() {
		bank.addSavingsAccount("12345678", 2.1);
		bank.depositById("12345678", 200);
		commandProcessor.process("withdraw 12345678 200");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(0, actual);
	}

	@Test
	void withdrawing_from_checking_subtracts_amount_from_balance() {
		bank.addCheckingAccount("12345678", 2.1);
		bank.depositById("12345678", 250);
		commandProcessor.process("withdraw 12345678 150");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(100, actual);
	}

	@Test
	void withdraw_zero_from_checking() {
		bank.addCheckingAccount("12345678", 2.1);
		bank.depositById("12345678", 200);
		commandProcessor.process("withdraw 12345678 0");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(200, actual);
	}

	@Test
	void withdraw_max_from_checking() {
		bank.addCheckingAccount("12345678", 2.1);
		bank.depositById("12345678", 500);
		commandProcessor.process("withdraw 12345678 400");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(100, actual);
	}

	@Test
	void withdraw_balance_from_checking() {
		bank.addCheckingAccount("12345678", 2.1);
		bank.depositById("12345678", 200);
		commandProcessor.process("withdraw 12345678 200");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(0, actual);
	}

	@Test
	public void withdraw_from_cd_after_12_months_pass() {
		bank.addCDAccount("12345678", 2.1, 2000);
		bank.passTime(12);
		commandProcessor.process("withdraw 12345678 2174.62132383101334");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(0, actual);
	}

	@Test
	public void withdraw_from_checking_twice() {
		bank.addCheckingAccount("12345678", 2.1);
		bank.depositById("12345678", 1000);
		commandProcessor.process("withdraw 12345678 250");
		commandProcessor.process("withdraw 12345678 250");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(500, actual);
	}

	@Test
	public void withdraw_from_savings_again_after_pass_time() {
		bank.addSavingsAccount("12345678", 2.1);
		bank.depositById("12345678", 2500);
		commandProcessor.process("withdraw 12345678 1000");
		bank.passTime(1);
		commandProcessor.process("withdraw 12345678 1000");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(502.625, actual);
	}

	@Test
	void overdraft_brings_savings_balance_to_zero() {
		bank.addSavingsAccount("12345678", 2.1);
		bank.depositById("12345678", 100);
		commandProcessor.process("withdraw 12345678 120");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(0, actual);
	}

	@Test
	void overdraft_brings_checking_balance_to_zero() {
		bank.addCheckingAccount("12345678", 2.1);
		bank.depositById("12345678", 100);
		commandProcessor.process("withdraw 12345678 120");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(0, actual);
	}

	@Test
	void overdraft_brings_cd_balance_to_zero() {
		bank.addCDAccount("12345678", 2.1, 2000);
		bank.passTime(12);
		commandProcessor.process("withdraw 12345678 5000");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(0, actual);
	}
}