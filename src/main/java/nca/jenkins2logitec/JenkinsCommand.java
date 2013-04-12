package nca.jenkins2logitec;

import nca.any2logitec.api.Command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JenkinsCommand implements Command {

	private static Logger logger = LoggerFactory.getLogger(JenkinsCommand.class);

	@Override
	public void execute() {
		logger.info("Jenkins command would now be invoked ...");
	}

}
