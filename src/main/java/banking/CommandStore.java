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
		validCommands.add(command);
	}

	public List<String> getAllInvalidCommands() {
		return new ArrayList<>(invalidCommands);
	}

	public List<String> getAllValidCommands() {
		return new ArrayList<>(validCommands);
	}
}