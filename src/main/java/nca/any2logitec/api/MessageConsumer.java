package nca.any2logitec.api;

import java.util.List;

public interface MessageConsumer {
	/**
	 * Reads the messages from the device and creates the command keys.
	 */
	List<CommandKey> consume();
}
