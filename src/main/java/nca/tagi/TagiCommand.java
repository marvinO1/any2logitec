package nca.tagi;

import java.awt.Desktop;
import java.net.URI;

import nca.any2logitec.api.Command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TagiCommand implements Command {

	private static Logger logger = LoggerFactory.getLogger(TagiCommand.class);
	
	@Override
	public void execute(long correlationId) {
		logger.info("TagiCommand, correltaionId: {}", correlationId);
		
		if (TagiAdapter.getFeedItem() != null) {
			try {
			  Desktop.getDesktop().browse(new URI(TagiAdapter.getFeedItem().getLink()));
			} catch (Exception ex) {
			  logger.error("Could not launch browser ...", ex);
			} 
		}
	}
}
