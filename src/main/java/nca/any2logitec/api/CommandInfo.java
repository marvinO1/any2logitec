package nca.any2logitec.api;

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