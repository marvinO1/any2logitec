package nca.any2logitec.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import nca.any2logitec.api.MessageProducer;

/**
 * Writes messages to the given inbound path.
 */
public class FileBasedMessageProducer implements MessageProducer  {
	
	private Path inboundPath;
	
	public FileBasedMessageProducer(Path inboundPath) {
		this.inboundPath = inboundPath;
	}
	
	@Override
	public void produce(List<String> messages, String feedName, int desiredDisplayTimeInSeconds) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		sb.append(feedName).append(".").append(desiredDisplayTimeInSeconds).append(".message");
		Files.write(this.inboundPath.resolve(sb.toString()), messages, StandardCharsets.UTF_8);		
	}
}