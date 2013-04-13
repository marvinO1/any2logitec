package nca.any2logitec.api;

/**
 * Represents a registered command. The {@link CommdanKey} is linked to the buttons
 * of the game pad. The {@code correlationId} is used to identify the command request
 * as well as to look up any information used to execute the command.
 * 
 * @see Command
 */
public class CommandInfo {

	private final CommandKey key;
	private final long correlationId;
	
	public CommandInfo(CommandKey key, long correlationId) {
		this.key = key;
		this.correlationId = correlationId;
	}
	
	public CommandKey getKey() {
		return key;
	}
	
	public long getCorrelationInfo() {
		return correlationId;
	}
}