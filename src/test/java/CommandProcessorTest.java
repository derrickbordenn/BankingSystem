import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	public static final int ID = 12345678;
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		commandProcessor = new CommandProcessor();
		bank = new Bank();
	}

	@Test
	void create_valid_cd_account() {
		Account actual = commandProcessor.Process("create checkinG 12345678 1.8");

		assertEquals(bank.getAccountById(ID), actual);
	}
}
