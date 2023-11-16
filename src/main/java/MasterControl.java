public class MasterControl {
	private CommandValidator commandValidator;
	private CommandProcessor commandProcessor;
	private InvalidCommands invalidCommands;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			InvalidCommands invalidCommands) {
		this.commandValidator = commandValidator;
		this.commandProcessor = commandProcessor;
		this.invalidCommands = invalidCommands;
	}
}
