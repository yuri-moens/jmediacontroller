package be.iswleuven.mediacontroller.player;

public class Song {

  /**
   * The song name.
   */
  private final String name;
  
  /**
   * The song's stream url.
   */
  private final String url;
  
  /**
   * Create a new song.
   * 
   * @param name
   * @param url
   */
  public Song(String name, String url) {
    this.name = name;
    this.url = url;
  }
  
  /**
   * Get the song name.
   * 
   * @return
   */
  public String getName() {
    return this.name;
  }
  
  /**
   * Get the song's stream url.
   * 
   * @return
   */
  public String getUrl() {
    return this.url;
  }
  
}
