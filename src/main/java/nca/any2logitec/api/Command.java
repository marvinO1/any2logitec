package nca.any2logitec.api;

/**
 * Logic that can be executed due to some feedback from the pad. 
 */
public interface Command {
	
	/**
	 * Executes the command logic. Use the correlationId to look up data required 
	 * to execute the function.
	 */
	void execute(long correlationId);
}
