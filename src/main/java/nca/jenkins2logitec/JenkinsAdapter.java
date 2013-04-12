package nca.jenkins2logitec;

import java.net.URL;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

import nca.any2logitec.api.Adapter;
import nca.any2logitec.api.MessageProducer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JenkinsAdapter implements Adapter  {

	private static Logger logger = LoggerFactory.getLogger(JenkinsAdapter.class);
	private JobStatusColor colorToTrigger;
	private URL url;
	private MessageProducer messageProducer;
    private long numberOfReportedProjects = 0;
    
    
	public JenkinsAdapter(URL jenkinsRestUrl, JobStatusColor colorToTrigger,
			MessageProducer messageProducer) {
		this.url = jenkinsRestUrl;
		this.colorToTrigger = colorToTrigger;
		this.messageProducer = messageProducer;
	}

	@Override
	public void produceInfo() {		
		try {
		  List<Project> projectsInTroubleList = getProjectsInTrouble();
		  
		  if (projectsInTroubleList.size() != 0 ) {
			  
			  for (Project p : projectsInTroubleList) {
				  List<String> messages = new ArrayList<String>();
				  messages.add(p.getMessage());
				  this.messageProducer.produce(messages, "jenkins", 10, p.getId());
			  }			  
			  this.numberOfReportedProjects = projectsInTroubleList.size();
		  } else {
			  if (this.numberOfReportedProjects != 0) {
				  // clear display
				 this.messageProducer.produce(new ArrayList<String>(), "jenkins", 10, 0);
			  } else {
				 this.logger.info("No news aree good news and therefore not sent to the pad ...");  
			  }
		  }
		  
		} catch (Exception ex) {
	   	  logger.error("Failed to read project state", ex);
		}		
	}
	
	@SuppressWarnings("unchecked")
	public List<Project> getProjectsInTrouble() throws DocumentException {
		List<Project> l = new ArrayList<Project>();

		Document dom = new SAXReader().read(url);

		logger.info("Fetching job states from jenkins ...");
		for (Element job : (List<Element>) dom.getRootElement().elements("job")) {
			
			String jobName = job.elementText("name");
			if (colorToTrigger.name().equalsIgnoreCase(job.elementText("color"))) {

				Element build = job.element("build");
				String url = build.elementText("url");
				long id = Long.parseLong(build.elementText("number"));
				
				Project p = new Project(job.elementText("name"), id, url);
				JenkinsCommand.registerProjectForCorrelationId(id,  p);
				
				StringBuilder sb = new StringBuilder();
				sb.append("Job: ");
				sb.append(jobName).append(", #").append(id).append(" not ok!");
				p.setMessage(sb.toString());
				
				l.add(p);
				logger.info(sb.toString());
			}
		}
		return l;
	}
}
