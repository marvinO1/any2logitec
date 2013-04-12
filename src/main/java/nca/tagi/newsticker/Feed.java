package nca.tagi.newsticker;

import java.util.ArrayList;
import java.util.List;


public class Feed {
  public Feed() {
    this.items = new ArrayList<>();
  }
  
  
  public void addItem(FeedItem item) {
    this.items.add(item);
  }

  
  @Override
  public String toString() {
    StringBuffer str = new StringBuffer();
    
    str.append("Feed: " + getTitle() + "   (#items=" + this.items.size() + ")\n");
    
    for (FeedItem item : this.items) {
      str.append("   " + item.toString() + "\n");
    }
    
    return str.toString();
  }
  
  // Getters & Setters
  public String getTitle() {
    return title;
  }

  
  public void setTitle(String title) {
    this.title = title;
  }
  
  
  public List<FeedItem> getItems() {
    return this.items;
  }


  // Properties
  private String         title;
  private List<FeedItem> items;
}
