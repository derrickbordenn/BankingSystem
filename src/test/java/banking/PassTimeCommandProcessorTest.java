package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeCommandProcessorTest {
	Bank bank;
	CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void pass_time_accrues_savings_apr() {
		bank.addSavingsAccount("10000000", 3);
		bank.depositById("10000000", 1000);
		commandProcessor.process("pass 1");
		double actual = bank.getAccountById("10000000").getBalance();

		assertEquals(1002.5, actual);
	}

	@Test
	void pass_time_accrues_checking_apr() {
		bank.addCheckingAccount("12345678", 3);
		bank.depositById("12345678", 1000);
		commandProcessor.process("pass 1");
		double actual = bank.getAccountById("12345678").getBalance();

		assertEquals(1002.5, actual);
	}

	@Test
	void pass_time_accrues_cd_apr() {
		bank.addCDAccount("99999999", 2.1, 2000);
		commandProcessor.process("pass 1");
		double actual = bank.getAccountById("99999999").getBalance();

		assertEquals(2014.0, actual);
	}

	@Test
	void pass_time_removes_empty_checking() {
		bank.addCheckingAccount("10000000", 3);
		commandProcessor.process("pass 1");
		Account actual = bank.getAccountById("12345678");

		assertEquals(null, actual);
	}

	@Test
	void pass_time_removes_empty_savings() {
		bank.addSavingsAccount("10000000", 3);
		commandProcessor.process("pass 1");
		Account actual = bank.getAccountById("12345678");

		assertEquals(null, actual);
	}

	@Test
	void pass_time_removes_empty_cd() {
		bank.addCDAccount("99999999", 2.1, 2000);
		bank.passTime(12);
		commandProcessor.process("pass 12");
		double balance = bank.getAccountById("99999999").getBalance();
		bank.withdrawById("99999999", balance);
		commandProcessor.process("pass 1");
		Account actual = bank.getAccountById("99999999");

		assertEquals(null, actual);
	}

	@Test
	void pass_maximum_time_accrues_savings() {
		bank.addSavingsAccount("10000000", 3);
		bank.depositById("10000000", 1000);
		commandProcessor.process("pass 60");
		double actual = bank.getAccountById("10000000").getBalance();

		assertEquals(1161.6167815552735, actual);
	}

	@Test
	void pass_maximum_time_accrues_checking() {
		bank.addCheckingAccount("10000000", 3);
		bank.depositById("10000000", 1000);
		commandProcessor.process("pass 60");
		double actual = bank.getAccountById("10000000").getBalance();

		assertEquals(1161.6167815552735, actual);
	}

	@Test
	void pass_maximum_time_accrues_cd() {
		bank.addCDAccount("99999999", 2.1, 2000);
		commandProcessor.process("pass 60");
		double actual = bank.getAccountById("99999999").getBalance();

		assertEquals(3039.4725733134796, actual);
	}
}
