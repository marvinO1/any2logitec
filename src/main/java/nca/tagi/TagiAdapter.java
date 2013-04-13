package nca.tagi;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nca.any2logitec.api.Adapter;
import nca.any2logitec.api.DisplayMessage;
import nca.any2logitec.api.MessageProducer;
import nca.tagi.newsticker.Feed;
import nca.tagi.newsticker.FeedItem;
import nca.tagi.newsticker.FeedManager;
import nca.tagi.newsticker.FeedReader;

public class TagiAdapter implements Adapter {

	private static Logger logger = LoggerFactory.getLogger(TagiAdapter.class);
	
	private final FeedReader feedReader;
	private final MessageProducer messageProducer;
	private FeedManager feedManager;
	private long correlationId = 0;
	
	
	public TagiAdapter(FeedReader feedReader, MessageProducer messageProducer) {
		this.feedReader = feedReader;
		this.messageProducer = messageProducer;
	}

	@Override
	public void produceInfo() {
		FeedItem item = null;
		try {
		  Feed feed = this.feedReader.read();
		  item = getFeedManager().processFeed(feed);
		  if (item != null) {
			logger.info("Adding new feed: {}", item);
			DisplayMessage msg = new DisplayMessage("tagi", 99, correlationId++);
			msg.setLines(item.description());			
			this.messageProducer.produce(msg);
			
		  }
		} catch (Exception ex) {
			logger.error("Failed to read tagi feed", ex);
		} finally {
			if (item != null) {
			  TagiCommand.setFeedItem(item);
			}
		}
	}
	
	protected FeedManager getFeedManager() throws Exception {
		if (this.feedManager == null) {
			Feed feed = this.feedReader.read();
			feed.getItems().remove(0);
			feed.getItems().remove(0);
			this.feedManager = new FeedManager(feed);
			
		}
		return this.feedManager;
	}
}
