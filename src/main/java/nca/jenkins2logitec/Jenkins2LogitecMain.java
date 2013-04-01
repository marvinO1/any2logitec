package  nca.jenkins2logitec;


import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nca.any2logitec.common.Any2Logitec;
import nca.any2logitec.common.MessageWriter;

/**
 * Main class for reading information from jenkins regarding the build state off all jobs.
 */
public class Jenkins2LogitecMain {
	
	private static Logger logger = LoggerFactory.getLogger(Jenkins2LogitecMain.class);
	
	public static void main(String[] args) throws InterruptedException {
		
		logger.info("Starting Jenkins2LogitecMain ...");
        if (args.length == 1) {
          String jenkisRestUrl = args[0];
          logger.info("Using jenkins REST API at: {}", jenkisRestUrl); 
          
          Path rootPath = Any2Logitec.getLogitecDisplayFolderRoot();
          logger.info("Using root path: {}", rootPath.toAbsolutePath()); 
          
          while (true) {
            try {
			 URL url = new URL(jenkisRestUrl);
			 JenkinsAdapter adapter = new JenkinsAdapter(url, JobStatusColor.RED);
			 List<String> projectsInTroubleList = adapter.getProjectsInTrouble();
			
			 Path pathToWrite = rootPath.resolve("jenkins.5.message");
			 MessageWriter.writeMessages(pathToWrite, projectsInTroubleList);
			
		    } catch (Exception e) {			
			  e.printStackTrace();
		    }
            pause(60);          
          }

        } else {
          System.out.println("Usage: java nca.jenkins2logitec.Jenkins2LogitecMain <jenkins REST url>");
        }
        logger.info("Jenkins2LogitecMain done!");
	}
	
	private static void pause(long seconds) throws InterruptedException {
		Thread.sleep(seconds * 1000);
	}
}
