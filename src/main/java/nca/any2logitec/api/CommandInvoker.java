package nca.any2logitec.api;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandInvoker extends TimerTask {

	private static Logger logger = LoggerFactory.getLogger(CommandInvoker.class);
	
	private final Path outboundPath;
	private final String feedName;
	private final Date startTime;
	private Timer timer;	
	private Map<CommandKey, Command> commands = new HashMap<CommandKey, Command>();	
	
	public CommandInvoker(Path outboundPath, String feedName) {
		this.outboundPath = outboundPath;
		this.feedName = feedName;
		this.startTime = new Date();
	} 	
	
	/**
	 * Starts the command invoker.
	 */
	public void start() {
		if (timer == null) { 
			timer = new Timer();
			timer.schedule(this, 1000, 1000);
		}
	}
	
	/**
	 * Stops the command invoker.
	 */
	public void stop() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	
	protected String getFeedPattern() {
		StringBuilder sb = new StringBuilder();
		if (this.feedName != null) {
			sb.append(this.feedName).append(".");
		}
		sb.append("*.pressed");
		return sb.toString();
	}
	
	@Override
	public void run() {
	
		String feedPatter = getFeedPattern();
		try (DirectoryStream<Path> commands = Files.newDirectoryStream(this.outboundPath, feedPatter)) {
		 for (Path p : commands) {
		   CommandKey key = CommandKey.fromFileRepresentation(p);
		   
		   if (!isCommandExpired(p)) {
			   invoke(key);   
		   }
		   removeCommandFile(p);
		 }
		} catch (IOException ex) {
		  logger.error("Failed to read commad file from: " + this.outboundPath, ex);
		}
	}
	
	public void register(CommandKey key, Command command) {
		commands.remove(key);
	    commands.put(key, command);
	}
	
	public void deregister(CommandKey key) {
		commands.remove(key);
	}
	
	public void invoke(CommandKey key) {		
		if (commands.containsKey(key)) {
		  commands.get(key).execute();
		} else {
		  logger.warn("No command registered for key: {}", key);
		}
	}

	/**
	 * Answers true in case the found command was created before the {@link CommandInvoker} was
	 * started and therefore is expired!
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
	
	protected void removeCommandFile(Path p) {
		try {
			Files.deleteIfExists(p);
		} catch (IOException ex) {
			logger.error("Failed to remove command file: " + p.toAbsolutePath(), ex);			
		}
	}
}
