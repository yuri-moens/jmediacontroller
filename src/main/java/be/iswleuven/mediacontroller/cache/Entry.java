package be.iswleuven.mediacontroller.cache;

import java.io.File;
import java.util.Date;

public class Entry implements Comparable<Entry> {
  
  /**
   * The url of the cached song.
   */
  private String url;
  
  /**
   * The title of the cached song.
   */
  private String title;
  
  /**
   * The path to the file of the cached song.
   */
  private String path;

  /**
   * The timestamp of when the song was cached.
   */
  private Date timestamp;
  
  /**
   * Create a new cache entry.
   * 
   * @param url
   * @param title
   * @param path
   */
  public Entry(String url, String title, String path) {
    this(url, title, path, new Date(System.currentTimeMillis()));
  }
  
  /**
   * Create a new cache entry.
   * 
   * @param url
   * @param title
   * @param path
   * @param timestamp
   */
  public Entry(String url, String title, String path, Date timestamp) {
    this.url = url;
    this.title = title;
    this.path = path;
    this.timestamp = timestamp;
  }
  
  /**
   * Get the url of cached song.
   * 
   * @return
   */
  public String getUrl() {
    return url;
  }

  /**
   * Get the title of the cached song.
   * 
   * @return
   */
  public String getTitle() {
    return title;
  }

  /**
   * Get the timestamp of when the song was cached.
   * 
   * @return
   */
  public Date getTimestamp() {
    return timestamp;
  }

  /**
   * Get the path to the file of the song that was cached.
   * 
   * @return
   */
  public String getPath() {
    return path;
  }
  
  /**
   * Delete the file at the entry path location.
   */
  public void deleteFile() {
    File file = new File(this.path);
    
    file.delete();
  }

  @Override
  public String toString() {
    return this.url + "\t" + this.title + "\t" + this.path + "\t" + this.timestamp.getTime();
  }
  
  @Override
  public boolean equals(Object other) {
    if (other instanceof Entry) {
      return this.url.equals(((Entry) other).url);
    }
    
    return false;
  }

  @Override
  public int compareTo(Entry other) {
    return this.timestamp.compareTo(other.timestamp);
  }
  
}
