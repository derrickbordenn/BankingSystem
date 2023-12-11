package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandProcessorTest {
	Bank bank;
	CommandProcessor commandProcessor;
	Account checking;
	Account savings;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);

		checking = new CheckingAccount(22222222, 2.1);
		savings = new SavingsAccount(11111111, 2.1);
	}

	@Test
	void transfer_from_checking_to_checking_increases_second_checking_balance() {
		bank.addAccount(checking);
		bank.depositById(22222222, 100);
		bank.addAccount(new CheckingAccount(12345678, 2.1));
		commandProcessor.process("transfer 22222222 12345678 50");
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(50, actual);
	}

	@Test
	void transfer_from_checking_to_checking_decreases_first_checking_balance() {
		bank.addAccount(checking);
		bank.depositById(22222222, 100);
		bank.addAccount(new CheckingAccount(12345678, 2.1));
		commandProcessor.process("transfer 22222222 12345678 50");
		double actual = bank.getAccountById(22222222).getBalance();

		assertEquals(50, actual);
	}

	@Test
	void transfer_from_checking_to_savings_increases_savings_balance() {
		bank.addAccount(checking);
		bank.depositById(22222222, 100);
		bank.addAccount(savings);
		commandProcessor.process("transfer 22222222 11111111 50");
		double actual = bank.getAccountById(22222222).getBalance();

		assertEquals(50, actual);
	}

	@Test
	void transfer_from_savings_to_checking_increases_checking_balance() {
		bank.addAccount(savings);
		bank.depositById(11111111, 100);
		bank.addAccount(checking);
		commandProcessor.process("transfer 11111111 22222222 50");
		double actual = bank.getAccountById(11111111).getBalance();

		assertEquals(50, actual);
	}

	@Test
	void transfer_from_savings_to_savings_increases_second_savings_balance() {
		bank.addAccount(savings);
		bank.depositById(11111111, 100);
		bank.addAccount(new SavingsAccount(12345678, 2.4));
		commandProcessor.process("transfer 11111111 12345678 50");
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(50, actual);
	}

	@Test
	void transfer_from_savings_to_savings_decreases_first_savings_balance_by_amount() {
		bank.addAccount(savings);
		bank.depositById(11111111, 100);
		bank.addAccount(new SavingsAccount(12345678, 2.4));
		commandProcessor.process("transfer 11111111 12345678 50");
		double actual = bank.getAccountById(11111111).getBalance();

		assertEquals(50, actual);
	}

	@Test
	void transfer_more_than_balance_from_checking_transfers_balance_to_second_account() {
		bank.addAccount(checking);
		bank.depositById(22222222, 100);
		bank.addAccount(new CheckingAccount(12345678, 2.4));
		commandProcessor.process("transfer 22222222 12345678 200");
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(100, actual);
	}

	@Test
	void transfer_more_than_balance_from_checking_brings_its_balance_to_zero() {
		bank.addAccount(checking);
		bank.depositById(22222222, 100);
		bank.addAccount(new CheckingAccount(12345678, 2.4));
		commandProcessor.process("transfer 22222222 12345678 200");
		double actual = bank.getAccountById(22222222).getBalance();

		assertEquals(0, actual);
	}

	@Test
	void transfer_more_than_balance_from_savings_transfers_balance_to_second_account() {
		bank.addAccount(savings);
		bank.depositById(11111111, 100);
		bank.addAccount(new SavingsAccount(12345678, 2.4));
		commandProcessor.process("transfer 11111111 12345678 200");
		double actual = bank.getAccountById(12345678).getBalance();

		assertEquals(100, actual);
	}

	@Test
	void transfer_more_than_balance_from_savings_brings_its_balance_to_zero() {
		bank.addAccount(savings);
		bank.depositById(11111111, 100);
		bank.addAccount(new SavingsAccount(12345678, 2.4));
		commandProcessor.process("transfer 11111111 12345678 200");
		double actual = bank.getAccountById(11111111).getBalance();

		assertEquals(0, actual);
	}

	@Test
	void transfer_twice_from_checking() {
		bank.addAccount(checking);
		bank.depositById(22222222, 100);
		bank.addAccount(new CheckingAccount(12345678, 2.4));
		commandProcessor.process("transfer 22222222 12345678 20");
		commandProcessor.process("transfer 22222222 12345678 20");
		double actual = bank.getAccountById(22222222).getBalance();

		assertEquals(60, actual);
	}

	@Test
	void transfer_from_savings_again_after_pass_time() {
		bank.addAccount(savings);
		bank.depositById(11111111, 1000);
		bank.addAccount(new SavingsAccount(12345678, 2.4));
		bank.depositById(12345678, 150);
		bank.addAccount(new SavingsAccount(99999999, 0));
		bank.depositById(99999999, 150);
		commandProcessor.process("transfer 11111111 12345678 20");
		bank.passTime(2);
		commandProcessor.process("transfer 11111111 99999999 20");
		double actual = bank.getAccountById(99999999).getBalance();

		assertEquals(170, actual);
	}
}
