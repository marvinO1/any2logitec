package nca.tagi.newsticker;

import java.util.ArrayList;
import java.util.List;


public class FeedManager {

	
  public FeedManager(Feed previousFeed) {
    this.previousFeed = previousFeed;
  }
  
  
  public FeedItem processFeed(Feed currentFeed) {
    // Determine difference
    List<FeedItem> difference = new ArrayList<>(currentFeed.getItems());
    difference.removeAll(this.previousFeed.getItems());
    
    // If there are no differences, we're done.
    if (difference.isEmpty()) {
      this.previousFeed = currentFeed;
      return null;
    }
    
    // If there is exactly one difference, we consume it.
    else if (difference.size() == 1) {
      this.previousFeed = currentFeed;
      return difference.get(0);
    }
    
    
    // If there's more than one difference, we consume the oldest (last) one.
    else {
      FeedItem lastItem = difference.get(difference.size()-1);
      this.previousFeed.addItem(lastItem);
      
      return lastItem;
    }
  }
  
  
  private Feed previousFeed;
}
