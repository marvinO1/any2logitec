package nca.any2logitec.api;

import java.util.List;

/**
 * Consumes messages produced by the game pad.
 */
public interface MessageConsumer {
	/**
	 * Reads the messages from the device and creates the {@link CommandInfo}.
	 */
	List<CommandInfo> consume();
}
