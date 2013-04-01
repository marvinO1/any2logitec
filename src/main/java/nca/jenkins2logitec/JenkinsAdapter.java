package nca.jenkins2logitec;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JenkinsAdapter {

	private static Logger logger = LoggerFactory.getLogger(JenkinsAdapter.class);
	private JobStatusColor colorToTrigger;
	private URL url;
	
	public JenkinsAdapter(URL jenkinsRestUrl, JobStatusColor colorToTrigger) {
		this.url = jenkinsRestUrl;
		this.colorToTrigger = colorToTrigger;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> getProjectsInTrouble() throws DocumentException {
		List<String> l = new ArrayList<String>();
		
		Document dom = new SAXReader().read(url);
		
		logger.info("Fetching job states from jenkins ..."); 
		for( Element job : (List<Element>)dom.getRootElement().elements("job")) {
			String jobName = job.elementText("name");
			if (colorToTrigger.name().equalsIgnoreCase(job.elementText("color"))) {
	
				StringBuilder sb = new StringBuilder();
				sb.append("Jenkins: ");
				sb.append(jobName).append(" not ok!");
				l.add(sb.toString());
				logger.info(sb.toString()); 
			}
		}
		return l;
	}
}