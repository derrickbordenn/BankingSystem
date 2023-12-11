package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeCommandProcessorTest {
	Bank bank;
	CommandProcessor commandProcessor;
	Account checking;
	Account savings;
	Account cd;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		checking = new CheckingAccount(12345678, 3);
		savings = new SavingsAccount(10000000, 3);
		cd = new CDAccount(99999999, 2.1, 2000);
	}

	@Test
	void pass_time_accrues_savings_apr() {
		bank.addAccount(savings);
		bank.depositById(10000000, 1000);
		commandProcessor.process("pass 1");
		double actual = savings.getBalance();

		assertEquals(1002.5, actual);
	}

	@Test
	void pass_time_accrues_checking_apr() {
		bank.addAccount(checking);
		bank.depositById(12345678, 1000);
		commandProcessor.process("pass 1");
		double actual = checking.getBalance();

		assertEquals(1002.5, actual);
	}

	@Test
	void pass_time_accrues_cd_apr() {
		bank.addAccount(cd);
		commandProcessor.process("pass 1");
		double actual = cd.getBalance();

		assertEquals(2014.0, actual);
	}

	@Test
	void pass_time_removes_empty_checking() {
		bank.addAccount(checking);
		commandProcessor.process("pass 1");
		Account actual = bank.getAccountById(12345678);

		assertEquals(null, actual);
	}

	@Test
	void pass_time_removes_empty_savings() {
		bank.addAccount(checking);
		commandProcessor.process("pass 1");
		Account actual = bank.getAccountById(10000000);

		assertEquals(null, actual);
	}

	@Test
	void pass_time_removes_empty_cd() {
		bank.addAccount(new CDAccount(99999999, 2.1, 0));
		commandProcessor.process("pass 1");
		Account actual = bank.getAccountById(99999999);

		assertEquals(null, actual);
	}

	@Test
	void pass_maximum_time_accrues_savings() {
		bank.addAccount(savings);
		bank.depositById(10000000, 1000);
		commandProcessor.process("pass 60");
		double actual = savings.getBalance();

		assertEquals(1161.6167815552735, actual);
	}

	@Test
	void pass_maximum_time_accrues_checking() {
		bank.addAccount(checking);
		bank.depositById(12345678, 1000);
		commandProcessor.process("pass 60");
		double actual = checking.getBalance();

		assertEquals(1161.6167815552735, actual);
	}

	@Test
	void pass_maximum_time_accrues_cd() {
		bank.addAccount(cd);
		commandProcessor.process("pass 60");
		double actual = cd.getBalance();

		assertEquals(3039.4725733134796, actual);
	}
}
