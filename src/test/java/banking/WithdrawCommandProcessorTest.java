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
		savings = new SavingsAccount(12345678, 2.4);
		checking = new CheckingAccount(12345679, 2.4);
		cd = new CDAccount(12345678, 2.1, 2000);
	}

	@Test
	public void withdrawing_from_savings_subtracts_amount_from_balance() {
		bank.addAccount(savings);
		bank.depositById(12345678, 250);
		commandProcessor.process("withdraw 12345678 150");
		double actual = savings.getBalance();

		assertEquals(100, actual);
	}
}