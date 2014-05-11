package nca.any2logitec.impl;

import java.io.FileReader;
import java.net.URL;
import java.util.Properties;
import java.util.Timer;

import nca.any2logitec.api.Adapter;
import nca.any2logitec.api.Any2LogitecEnvironment;
import nca.any2logitec.api.CommandKey;
import nca.any2logitec.api.MessageConsumer;
import nca.any2logitec.api.MessageProducer;
import nca.jenkins2logitec.JenkinsAdapter;
import nca.jenkins2logitec.JenkinsCommand;
import nca.jenkins2logitec.JobStatusColor;
import nca.tagi.TagiAdapter;
import nca.tagi.TagiCommand;
import nca.tagi.newsticker.FeedReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for reading
 */
public class LogitecHub {

	private static Logger logger = LoggerFactory.getLogger(LogitecHub.class);

	public static void main(String[] args) throws Exception {

		try {
			logger.info("Starting LogitecHub ...");
			jenkins();
			tagi();
			logger.info("done!");
			
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

	// -- JENKINS --------------------------------------------------------------------------------

	protected static void jenkins() throws Exception {		
		logger.info("Install Jenkins Adapter ...");
		Timer adapterTimer = new Timer();
		adapterTimer.schedule(new AdapterTask(getJenkinsAdapter()), 1000L, 10*3000L);
		logger.info("done!");
		logger.info("Install Jenkins Command Invoker ...");
		CommandInvoker commandInvoker = getJenkinsCommandInvoker();
		commandInvoker.start();		
		logger.info("done!");
	}

	protected static Adapter getJenkinsAdapter() throws Exception {
		Properties prop = new Properties();
		prop.load(new FileReader(Any2LogitecEnvironment.getLogitecHubCfgFolder().resolve("jenkins.prop").toFile()));
		String urlStr = prop.getProperty("jenkins.api.url", "http://localhost:8080/jenkins/api/xml?depth=1");
		URL url = new URL(urlStr);
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

	

	// -- Tagi -----------------------------------------------------------------------------------
	protected static void tagi() throws Exception {		
		logger.info("Install Tagi Adapter ...");				
		Timer adapterTimer = new Timer();
		adapterTimer.schedule(new AdapterTask(getTagiAdapter()), 2000L, 20*1000L);
		logger.info("done!");
		logger.info("Install Tagi Command Invoker ...");
		CommandInvoker commandInvoker = getTagiCommandInvoker();
		commandInvoker.start();
		logger.info("done!");
	}
	
	protected static Adapter getTagiAdapter() throws Exception {
		return new TagiAdapter(getTagiFeedReader(), getMessageProducer());
	}
	
	
	protected static FeedReader getTagiFeedReader() throws Exception {
		
		Properties prop = new Properties();
		prop.load(new FileReader(Any2LogitecEnvironment.getLogitecHubCfgFolder().resolve("tagi.prop").toFile()));
		String urlStr = prop.getProperty("tagi.rss_ticker.url", "http://www.tagesanzeiger.ch/rss_ticker.html");
		URL url = new URL(urlStr);
		return new FeedReader(url);
	}
	
	protected static CommandInvoker getTagiCommandInvoker() {
		MessageConsumer messageConsumer = new FileBasedMessageConsumer(
				Any2LogitecEnvironment.getLogitecHubOutboundFolder(), "tagi");

		CommandInvoker commandInvoker = new CommandInvoker(messageConsumer);
		commandInvoker.register(CommandKey.BUTTON_3, new TagiCommand());		
		return commandInvoker;
	}
	
	// -- Common -----------------------------------------------------------------------------------
	protected static MessageProducer getMessageProducer() {
		return new FileBasedMessageProducer(Any2LogitecEnvironment.getLogitecHubInboundFolder());
	}
}
