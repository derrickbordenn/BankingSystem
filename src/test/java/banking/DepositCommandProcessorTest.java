package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandProcessorTest {
	Bank bank;
	CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void deposit_zero_to_checking() {
		bank.addAccount(new CheckingAccount(12345678, 2.4));
		commandProcessor.process("deposit 12345678 0");
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(0, actual);
	}

	@Test
	void deposit_zero_to_savings() {
		bank.addAccount(new SavingsAccount(12345678, 2.4));
		commandProcessor.process("deposit 12345678 0");
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(0, actual);
	}

	@Test
	void deposit_to_empty_checking_makes_balance_deposit_amount() {
		bank.addAccount(new CheckingAccount(12345678, 2.4));
		commandProcessor.process("deposit 12345678 200");
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(200, actual);
	}

	@Test
	void deposit_to_empty_savings_makes_balance_deposit_amount() {
		bank.addAccount(new SavingsAccount(12345678, 2.4));
		commandProcessor.process("deposit 12345678 200");
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(200, actual);
	}

	@Test
	void deposit_to_checking_with_existing_balance_adds_deposit_amount_to_balance() {
		bank.addAccount(new CheckingAccount(12345678, 2.4));
		bank.depositById(12345678, 200);
		commandProcessor.process("deposit 12345678 27");
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(227, actual);
	}

	@Test
	void deposit_to_savings_with_existing_balance_adds_deposit_amount_to_balance() {
		bank.addAccount(new SavingsAccount(12345678, 2.4));
		bank.depositById(12345678, 200);
		commandProcessor.process("deposit 12345678 27");
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(227, actual);
	}

	@Test
	void deposit_maximum_to_checking() {
		bank.addAccount(new CheckingAccount(12345678, 2.4));
		commandProcessor.process("deposit 12345678 1000");
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(1000, actual);
	}

	@Test
	void deposit_maximum_to_savings() {
		bank.addAccount(new SavingsAccount(12345678, 2.4));
		commandProcessor.process("deposit 12345678 2500");
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(2500, actual);
	}
}
