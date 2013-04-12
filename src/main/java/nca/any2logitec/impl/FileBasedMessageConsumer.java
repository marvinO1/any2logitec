package nca.any2logitec.impl;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nca.any2logitec.api.CommandInfo;
import nca.any2logitec.api.CommandKey;
import nca.any2logitec.api.MessageConsumer;

public class FileBasedMessageConsumer implements MessageConsumer {

	private static Logger logger = LoggerFactory
			.getLogger(FileBasedMessageConsumer.class);

	private final Path outboundPath;
	private final String feedName;
	private final Date startTime;

	public FileBasedMessageConsumer(Path outboundPath, String feedName) {
		this.outboundPath = outboundPath;
		this.feedName = feedName;
		this.startTime = new Date();
	}

	@Override
	public List<CommandInfo> consume() {
		List<CommandInfo> messages = new ArrayList<CommandInfo>();

		String feedPatter = getFeedPattern();
		try (DirectoryStream<Path> commands = Files.newDirectoryStream(
				this.outboundPath, feedPatter)) {
			for (Path p : commands) {
				CommandKey key = CommandKey.fromFileRepresentation(p, this.feedName);
				
				Long id = stripCorrelationId(p); 
				CommandInfo ci = new CommandInfo(key, id);

				if (!isCommandExpired(p)) {
					messages.add(ci);
				}
				removeCommandFile(p);
			}
		} catch (IOException ex) {
			logger.error("Failed to read commad file from: " + this.outboundPath, ex);
		}
		return messages;
	}
	
	/**
	 * Answers true in case the found command was created before the
	 * {@link CommandInvoker} was started and therefore is expired!
	 * 
	 * @throws IOException
	 */
	protected boolean isCommandExpired(Path p) throws IOException {
		boolean ret = false;
		try {
			FileTime fileTime = Files.getLastModifiedTime(p, LinkOption.NOFOLLOW_LINKS);
			long ts = fileTime.to(TimeUnit.MILLISECONDS);
			if (ts < startTime.getTime()) {
				ret = false;
			}
		} catch (IOException ex) {
			logger.error("Could not read lastmodifiedTime on given path: " + p.toAbsolutePath(), ex);
		}
		return ret;
	}

	protected String getFeedPattern() {
		StringBuilder sb = new StringBuilder();
		if (this.feedName != null) {
			sb.append(this.feedName).append(".");
		}
		sb.append("*.pressed");
		return sb.toString();
	}

	protected void removeCommandFile(Path p) {
		try {
			Files.deleteIfExists(p);
		} catch (IOException ex) {
			logger.error("Failed to remove command file: " + p.toAbsolutePath(), ex);
		}
	}
	
	protected long stripCorrelationId(Path path) {
		logger.info("stripping correlationId from. {}", path.toAbsolutePath());
		
		String[] tok = path.toAbsolutePath().toString().split("\\.");
		
		
		return Long.valueOf(tok[tok.length -4]);
	}
}
