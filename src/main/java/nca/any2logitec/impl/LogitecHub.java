package nca.any2logitec.impl;

import java.net.MalformedURLException;
import java.net.URL;

import nca.any2logitec.api.Any2LogitecEnvironment;
import nca.any2logitec.api.CommandKey;
import nca.any2logitec.api.MessageConsumer;
import nca.any2logitec.api.MessageProducer;
import nca.jenkins2logitec.JenkinsAdapter;
import nca.jenkins2logitec.JenkinsCommand;
import nca.jenkins2logitec.JobStatusColor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for reading
 */
public class LogitecHub {

	private static Logger logger = LoggerFactory.getLogger(LogitecHub.class);

	public static void main(String[] args) throws Exception {

		try {
			jenkins();
		} catch (Exception ex) {
			logger.error("Failed to initialize components for jenkins ...", ex);
		}

		// wait for the ...
		try {
			for (;;) {
				Thread.sleep(10 * 1000);
			}
		} catch (InterruptedException e) {
			// nop
		}
		logger.info("LogitecHub done!");
	}

	// -- JENKINS
	// --------------------------------------------------------------------------------

	protected static void jenkins() throws MalformedURLException {
		logger.info("Starting LogitecHub ...");
		logger.info("Install Jenkins Adapter ...");
		Runnable ja = getJenkinsAdapter();
		new Thread(ja).start();
		logger.info("done!");
		logger.info("Install Jenkins Command Invoker ...");
		CommandInvoker commandInvoker = getJenkinsCommandInvoker();
		commandInvoker.start();
		logger.info("done!");
	}

	protected static Runnable getJenkinsAdapter() throws MalformedURLException {
		URL url = new URL("http://localhost:8080/jenkins/api/xml");
		JenkinsAdapter adapter = new JenkinsAdapter(url, JobStatusColor.RED, getMessageProducer());
		return adapter;
	}

	protected static CommandInvoker getJenkinsCommandInvoker() {
		MessageConsumer messageConsumer = new FileBasedMessageConsumer(
				Any2LogitecEnvironment.getLogitecHubOutboundFolder(), "jenkins");

		CommandInvoker commandInvoker = new CommandInvoker(messageConsumer);
		commandInvoker.register(CommandKey.BUTTON_0, new JenkinsCommand());
		return commandInvoker;
	}

	protected static MessageProducer getMessageProducer() {
		return new FileBasedMessageProducer(Any2LogitecEnvironment.getLogitecHubInboundFolder());
	}

	// -- Tagi
	// -----------------------------------------------------------------------------------
}
