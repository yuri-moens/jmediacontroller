package be.iswleuven.mediacontroller.player;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Song {

  /**
   * The song title.
   */
  private final String TITLE;
  
  /**
   * The song's stream url.
   */
  private final String URL;
  
  /**
   * The address of the client requesting the song.
   */
  private final InetAddress CLIENT_ADDRESS;
  
  /**
   * The timestamp at which the song was requested.
   */
  private final Date TIMESTAMP;
  
  /**
   * Create a new song.
   * 
   * @param name
   * @param url
   */
  public Song(String title, String url, InetAddress clientAddress) {
    if (title == null || url == null || clientAddress == null) {
      throw new IllegalArgumentException("Title, url and client address may not be null.");
    }
    
    this.TITLE = title;
    this.URL = url;
    this.CLIENT_ADDRESS = clientAddress;
    this.TIMESTAMP = new Date(System.currentTimeMillis());
  }
  
  /**
   * Get the song name.
   * 
   * @return
   */
  public String getTitle() {
    return this.TITLE;
  }
  
  /**
   * Get the song's stream url.
   * 
   * @return
   */
  public String getUrl() {
    return this.URL;
  }
  
  /**
   * Get the address of the client that requested the song.
   * 
   * @return
   */
  public InetAddress getClientAddress() {
    return this.CLIENT_ADDRESS;
  }
  
  /**
   * Get the timestamp at which the song was requested.
   * 
   * @return
   */
  public Date getTimestamp() {
    return this.TIMESTAMP;
  }
  
  @Override
  public String toString() {
    return "[" + new SimpleDateFormat("HH:mm:ss").format(this.TIMESTAMP) + "] "
        + this.TITLE + " (" + this.CLIENT_ADDRESS + ")";
  }
  
}
