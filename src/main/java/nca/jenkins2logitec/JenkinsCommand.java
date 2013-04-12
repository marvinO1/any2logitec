package nca.jenkins2logitec;

import java.awt.Desktop;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import nca.any2logitec.api.Command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JenkinsCommand implements Command {

	private static Logger logger = LoggerFactory.getLogger(JenkinsCommand.class);

	private static final Map<Long, Project> urlMap = new HashMap<Long, Project>();
	
	@Override
	public void execute(long correlationId) {
		logger.info("Jenkins command would now be invoked, correlationId: {}", correlationId);
		
		Project p = urlMap.get(correlationId);
		if (p != null) {				
			try {
			  Desktop.getDesktop().browse(new URI(p.getUrl()));
			} catch (Exception ex) {
			  logger.error("Could not launch browser ...", ex);
			} 	
		}
	}
	
	public static void registerProjectForCorrelationId(long id, Project project) {
		urlMap.put(id, project);		
		purge();
	}
	
	protected static void purge() {
		// here we would remove all projects older than n Minutes. Maybe there is a more
		// elegant way to have the cache purged without framework support.
		
		// TODO: write code ...
	}
}
