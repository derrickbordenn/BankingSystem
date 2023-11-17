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
		for (String command : input) {
			commandStore.addInvalidCommand(command);
		}
		return commandStore.getAllInvalidCommands();
	}
}
