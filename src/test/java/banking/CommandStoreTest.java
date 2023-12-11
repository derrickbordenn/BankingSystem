package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStoreTest {

	CommandStore commandStore;

	@BeforeEach
	void setUp() {
		commandStore = new CommandStore();
	}

	@Test
	void invalid_commands_starts_empty() {
		List<String> actual = commandStore.getAllInvalidCommands();
		assertEquals(0, actual.size());
	}

	@Test
	void valid_commands_starts_empty() {
		List<String> actual = commandStore.getAllValidCommands();
		assertEquals(0, actual.size());
	}

	@Test
	void add_invalid_command() {
		String invalidCommand = "invalid command";
		commandStore.addInvalidCommand(invalidCommand);
		List<String> actual = commandStore.getAllInvalidCommands();

		assertEquals("invalid command", actual.get(0));
	}

	@Test
	void only_one_invalid_command() {
		String invalidCommand = "invalid command";
		commandStore.addInvalidCommand(invalidCommand);
		List<String> actual = commandStore.getAllInvalidCommands();

		assertEquals(1, actual.size());
	}

	@Test
	void get_all_invalid_commands() {
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

	@Test
	void add_valid_command() {
		String validCommand = "valid command";
		commandStore.addValidCommand(validCommand);
		List<String> actual = commandStore.getAllValidCommands();

		assertEquals("Valid command", actual.get(0));
	}

	@Test
	void get_multiple_valid_commands() {
		String firstCommand = "first command";
		String secondCommand = "second command";
		String thirdCommand = "third command";
		commandStore.addValidCommand(firstCommand);
		commandStore.addValidCommand(secondCommand);
		commandStore.addValidCommand(thirdCommand);
		List<String> actual = commandStore.getAllValidCommands();

		assertEquals("First command", actual.get(0));
		assertEquals("Second command", actual.get(1));
		assertEquals("Third command", actual.get(2));
	}

	@Test
	void only_one_valid_command() {
		String validCommand = "invalid command";
		commandStore.addValidCommand(validCommand);
		List<String> actual = commandStore.getAllValidCommands();

		assertEquals(1, actual.size());
	}

}