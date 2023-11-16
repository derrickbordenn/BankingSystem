import java.util.List;

public class MasterControl {
	private CommandValidator commandValidator;
	private CommandProcessor commandProcessor;
	private CommandStore commandStore;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			CommandStore commandStore) {
		this.commandValidator = commandValidator;
		this.commandProcessor = commandProcessor;
		this.commandStore = commandStore;
	}

	public List<String> start(List<String> input) {
		commandStore.addInvalidCommand("creat checking 12345678 1.0");
		return commandStore.getAllInvalidCommands();
	}
}
