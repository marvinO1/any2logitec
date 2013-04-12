package nca.tagi.newsticker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class FeedItem {

  public static int MAX_LINES       = 4;
  public static int MAX_LINE_LENGTH = 25;
  
  
  // Constructor
  public FeedItem(String title, String link, Date published) {
    super();
    this.title = title;
    this.link = link;
    this.published = published;
  }
  
  
  public List<String> description() {
    
    List<String> lines = new ArrayList<>();
    
    // Line 1: Date & Time
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    lines.add(formatter.format(this.published));
    

    // Splitting title into single words
    String[] words = this.title.split("\\b+");
    
    for (int i=0; i<words.length; i++) {
      words[i] = truncate(words[i]);
    }

    
    // Rest of lines: Title text
    for (int lineNr=1, wordIndex=0; lineNr<MAX_LINES; lineNr++) {
      StringBuffer line = new StringBuffer();
      
      while (wordIndex < words.length) {
        if (line.length() + words[wordIndex].length() < MAX_LINE_LENGTH) {
          line.append(words[wordIndex++]);
        }
        else {
          break;
        }
      }
      
      lines.add(line.toString());
    }
        
    return lines;  
  }

  
  @Override
  public String toString() {
    StringBuffer str = new StringBuffer();
    
    if (getPublished() != null) {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      str.append(formatter.format(getPublished()));
    } else {
      str.append("??-??");
    }
    
    str.append(": " + getTitle());
    str.append(" [" + getLink() + "]");
    
    return str.toString();
  }
  
  
  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 31)
        .append(this.title)
        .append(this.link)
        .append(this.published)
        .toHashCode();
  }
  
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    
    if (obj == this) {
      return true;
    }
    
    if (obj.getClass() != getClass()) {
      return false;
    }
    
    FeedItem right = (FeedItem)obj;
    
    return new EqualsBuilder()
        .append(this.title, right.getTitle())
        .append(this.link, right.getLink())
        .append(this.published, right.getPublished())
        .isEquals();
  }

  
  private String truncate(String value)
  {
    if (value != null && value.length() > MAX_LINE_LENGTH)
      value = value.substring(0, MAX_LINE_LENGTH);
    return value;
  }
   
  
  // Getters & Setters  
  public String getTitle() {
    return title;
  }

  public String getLink() {
    return link;
  }

  public Date getPublished() {
    return published;
  }

  
  // Properties
  private String title;
  private String link;
  private Date   published;
}
