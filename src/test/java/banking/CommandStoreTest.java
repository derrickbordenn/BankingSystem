package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStoreTest {

	CommandStore commandStore;

	@BeforeEach
	public void setUp() {
		commandStore = new CommandStore();
	}

	@Test
	public void add_invalid_command() {
		String invalidCommand = "invalid command";
		commandStore.addInvalidCommand(invalidCommand);
		List<String> actual = commandStore.getAllInvalidCommands();

		assertEquals("invalid command", actual.get(0));
	}

	@Test
	public void get_all_invalid_commands() {
		String firstInvalidCommand = "first invalid command";
		String secondInvalidCommand = "second invalid command";
		String thirdInvalidCommand = "third invalid command";
		commandStore.addInvalidCommand(firstInvalidCommand);
		commandStore.addInvalidCommand(secondInvalidCommand);
		commandStore.addInvalidCommand(thirdInvalidCommand);
		List<String> actual = commandStore.getAllInvalidCommands();

		assertEquals("first invalid command", actual.get(0));
		assertEquals("second invalid command", actual.get(1));
		assertEquals("third invalid command", actual.get(2));
	}
}