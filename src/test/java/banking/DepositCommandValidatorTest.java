package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandValidatorTest {
	Bank bank;
	CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void deposit_to_checking() {
		bank.addAccount(new CheckingAccount(12345678, 2.4));
		bank.depositById(12345678, 200);
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(200, actual);
	}

	@Test
	void deposit_to_savings() {
		bank.addAccount(new SavingsAccount(12345678, 2.4));
		bank.depositById(12345678, 200);
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(200, actual);
	}
}
