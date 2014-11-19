package be.iswleuven.mediacontroller.player;

public interface Player {

  /**
   * Start playing.
   */
  void play();
  
  /**
   * Pause playing.
   */
  void pause();
  
  /**
   * Stop playing.
   */
  void stop();
  
  /**
   * Go to the next song in the playlist.
   */
  void next();
  
  /**
   * Go to the previous song in the playlist.
   */
  void previous();
  
  /**
   * Skip part of the song.
   * 
   * @param delta
   */
  void skip(long delta);
  
  /**
   * Change the volume by the given amount.
   * 
   * @param amount
   */
  void changeVolume(int amount);
  
  /**
   * Set the volume to the given amount.
   * 
   * @param amount
   */
  void setVolume(int amount);
  
}
