package nca.any2logitec.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import nca.any2logitec.api.DisplayMessage;
import nca.any2logitec.api.MessageProducer;

/**
 * Writes messages to the given inbound path.
 */
public class FileBasedMessageProducer implements MessageProducer {

	private Path inboundPath;

	public FileBasedMessageProducer(Path inboundPath) {
		this.inboundPath = inboundPath;
	}


	@Override
	public void produce(DisplayMessage message) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		sb.append(message.getFeedName()).append(".")
		   .append(message.getDesiredDisplayTimeInSeconds())
		   .append(".")
		   .append(message.getCorrelationId())
		   .append(".message");
		
		Files.write(this.inboundPath.resolve(sb.toString()), message.getLines(), StandardCharsets.ISO_8859_1);
	}
}