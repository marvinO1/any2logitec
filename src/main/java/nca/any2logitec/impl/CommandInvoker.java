package nca.any2logitec.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import nca.any2logitec.api.Command;
import nca.any2logitec.api.CommandInfo;
import nca.any2logitec.api.CommandKey;
import nca.any2logitec.api.MessageConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Invokes registered commands.
 */
public class CommandInvoker extends TimerTask {

	private static Logger logger = LoggerFactory.getLogger(CommandInvoker.class);

	private Timer timer;
	private final MessageConsumer messageConsumer;
	private final Map<CommandKey, Command> commands = new HashMap<CommandKey, Command>();

	public CommandInvoker(MessageConsumer messageConsumer) {
		this.messageConsumer = messageConsumer;
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

	@Override
	public void run() {
		List<CommandInfo> commandInfos = this.messageConsumer.consume();
		for (CommandInfo info : commandInfos) {
			invoke(info);
		}
	}

	public void register(CommandKey key, Command command) {
		commands.remove(key);
		commands.put(key, command);
	}

	public void deregister(CommandKey key) {
		commands.remove(key);
	}

	protected void invoke(CommandInfo info) {
		if (commands.containsKey(info.getKey())) {
			commands.get(info.getKey()).execute(info.getCorrelationInfo());
		} else {
			logger.warn("No command registered for key: {}", info.getKey());
		}
	}

}
