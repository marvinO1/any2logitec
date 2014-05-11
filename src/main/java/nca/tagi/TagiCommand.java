package nca.tagi;

import java.awt.Desktop;
import java.net.URI;

import nca.any2logitec.api.Command;
import nca.tagi.newsticker.FeedItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TagiCommand implements Command {

	private static Logger logger = LoggerFactory.getLogger(TagiCommand.class);
	
	private static FeedItem feedItem;
	
	@Override
	public void execute(long correlationId) {
		logger.info("TagiCommand, correltaionId: {}", correlationId);
		
		if (feedItem != null) {
			try {
			  Desktop.getDesktop().browse(new URI(feedItem.getLink()));
			} catch (Exception ex) {
			  logger.error("Could not launch browser ...", ex);
			} 
		}
	}
	
	public static void setFeedItem(FeedItem currentFeedItem) {
	  feedItem = currentFeedItem;
	}
}
