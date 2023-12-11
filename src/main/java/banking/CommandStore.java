package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStore {
	private List<String> invalidCommands;
	private List<String> validCommands;

	public CommandStore() {
		this.invalidCommands = new ArrayList<>();
		this.validCommands = new ArrayList<>();
	}

	public void addInvalidCommand(String command) {
		invalidCommands.add(command);
	}

	public void addValidCommand(String command) {
		String validCommand = command.substring(0, 1).toUpperCase() + command.substring(1).toLowerCase();
		validCommands.add(validCommand);
	}

	public List<String> getAllInvalidCommands() {
		return new ArrayList<>(invalidCommands);
	}

	public List<String> getAllValidCommands() {
		return new ArrayList<>(validCommands);
	}
}