package nca.jenkins2logitec;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nca.any2logitec.api.Adapter;
import nca.any2logitec.api.DisplayMessage;
import nca.any2logitec.api.MessageProducer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JenkinsAdapter implements Adapter  {

	
	private static Logger logger = LoggerFactory.getLogger(JenkinsAdapter.class);
	private static final String FEED_NAME = "jenkins";
	private URL url;
	private JobStatusColor colorToTrigger;	
	private MessageProducer messageProducer;
	private long correlationId = 0;
    private long numberOfReportedProjects = 0;
        
	public JenkinsAdapter(URL jenkinsRestUrl, JobStatusColor colorToTrigger, MessageProducer messageProducer) {
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
				  DisplayMessage msg = getProblemMessage(p);
				  this.messageProducer.produce(msg);
				  JenkinsCommand.registerProjectForCorrelationId(msg.getCorrelationId(),  p);
			  }			  
			  this.numberOfReportedProjects = projectsInTroubleList.size();
		  } else {
			  
			  if (this.numberOfReportedProjects != 0) {
				  // clear display
				 this.messageProducer.produce(getAllOkMessage());
				 this.numberOfReportedProjects = 0;
			  } else {
				 logger.info("No news are good news and therefore not sent to the pad ...");  
			  }
		  }
		  
		} catch (Exception ex) {
	   	  logger.error("Failed to read project state", ex);
		}		
	}
	
	protected DisplayMessage getProblemMessage(Project p) {
		DisplayMessage msg = new DisplayMessage(FEED_NAME, 99, correlationId);
		msg.addLine(p.getMessage())
		   .addLine("")
		   .addLine("")
		   .addLine("Info   NOP     NOP    NOP");
		correlationId++;
		return msg;
	}
	
	protected DisplayMessage getAllOkMessage() {
		DisplayMessage msg = new DisplayMessage(FEED_NAME, 99, 0); 
		msg.addLine("Jenkins projects are ok");
		return msg;
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
