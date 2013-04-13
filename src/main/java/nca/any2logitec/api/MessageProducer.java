package nca.any2logitec.api;


/**
 * Produces messages that get sent to the game pad.
 */
public interface MessageProducer {
	
	/**
	 * Sends the given messages to the game pad.
	 */
	void produce(DisplayMessage message) throws Exception;
}
