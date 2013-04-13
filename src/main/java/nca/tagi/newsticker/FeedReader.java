package nca.tagi.newsticker;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Provides functionality to read a feed given an URL
 */
public class FeedReader {
  
  private static Logger logger = LoggerFactory.getLogger(FeedReader.class);

  private final URL url;
  private static final String FEED_ITEM = "item";
  private static final String TITLE     = "title";
  private static final String LINK      = "link";
  private static final String PUBLISHED = "pubDate";
  
  public FeedReader(URL url) {
	  this.url = url;
  }
  
  public Feed read() throws Exception {

    String title        = null;
    String link         = null;
    Date   published    = null;
    
    boolean isFeedHeader = true;
    
    Feed   feed = new Feed();
    
    logger.info("Opening stream on: {}", url);
    InputStream in = url.openStream();
    
    XMLInputFactory inputFactory  = XMLInputFactory.newInstance();
    XMLEventReader  elementReader = inputFactory.createXMLEventReader(in);

    while (elementReader.hasNext()) {
      XMLEvent element = elementReader.nextEvent();
      
      // Handling starting elements
      if (element.isStartElement()) {
        String elementName = element.asStartElement().getName().getLocalPart();
        
        switch (elementName) {
          case FEED_ITEM:
            
            // Is the header now finished?
            if (isFeedHeader) {
              feed.setTitle(title);              
              isFeedHeader = false;
            }
            
            elementReader.nextEvent();

            break;
          
          case TITLE:
            title = nextString(elementReader);
            break;
            
          case LINK:
            link = nextString(elementReader);
            break;
            
          case PUBLISHED:
            String publishedString = nextString(elementReader);
            
            // Trying to parse the date string
            try {
              SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
              
              published = formatter.parse(publishedString);
            } catch(Exception exc) {
              exc.printStackTrace();
              published = null;
            }
            
            break;
        }
      }
      
      // Handling ending elements
      else if (element.isEndElement()) {
        String elementName = element.asEndElement().getName().getLocalPart();

        switch (elementName) {
          case FEED_ITEM:
            FeedItem item = new FeedItem(title, link, published);
            feed.addItem(item);

            elementReader.nextEvent();

            break;
        }
      }
    }
    
    return feed;
  }

  
  private String nextString(XMLEventReader elementReader) throws XMLStreamException {
  
    String result = "";
        
    XMLEvent element = elementReader.nextEvent();
    
    if (element instanceof Characters) {
      result = element.asCharacters().getData();
    }
    
    return result;
  }
}
