import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {

	MasterControl masterControl;

	@BeforeEach
	void setUp() {
		Bank bank = new Bank();
		masterControl = new MasterControl(new CommandValidator(), new CommandProcessor(bank), new CommandStore());
	}

	@Test
	void typo_in_create_command_is_invalid() {
		List<String> input = new ArrayList<>();
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		List<String> input = new ArrayList<>();
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertEquals("depositt 12345678 100", actual.get(0));
	}
}
