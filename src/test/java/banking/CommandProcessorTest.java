package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor((bank));
	}

	@Test
	void create_valid_savings_account() {
		commandProcessor.Process("create savingS 12345678 1.8");
		double actual = bank.getAccountById(12345678).getApr();

		assertEquals(1.8, actual);
	}

	@Test
	void create_valid_checking_account() {
		commandProcessor.Process("create cHecKinG 12345678 1.8");
		double actual = bank.getAccountById(12345678).getApr();

		assertEquals(1.8, actual);
	}

	@Test
	void create_valid_CD_account() {
		commandProcessor.Process("create Cd 12345678 2.4 1500");
		double actual = bank.getAccountById(12345678).getApr();

		assertEquals(2.4, actual);
	}

	@Test
	void deposit_to_savings_account() {
		commandProcessor.Process("create savingS 12345678 1.8");
		commandProcessor.Process("deposit 12345678 1000");
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(1000, actual);
	}

	@Test
	void deposit_to_checking_account() {
		commandProcessor.Process("create chEckIng 11111111 1.8");
		commandProcessor.Process("deposit 11111111 1000");
		double actual = bank.getAccountById(11111111).getBalance();

		assertEquals(1000, actual);
	}

	@Test
	void deposit_to_CD_account() {
		commandProcessor.Process("create CD 10101010 1.8 2500");
		commandProcessor.Process("deposit 10101010 1000");
		double actual = bank.getAccountById(10101010).getBalance();

		assertEquals(3500, actual);
	}
}