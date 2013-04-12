package nca.any2logitec.api;

import java.util.List;

public interface MessageProducer {
	void produce(List<String> messages, String feedName, int desiredDisplayTimeInSeconds) throws Exception;
}
