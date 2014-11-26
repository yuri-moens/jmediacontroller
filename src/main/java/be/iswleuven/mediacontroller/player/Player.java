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
   * Go to the previous song in the history.
   */
  void previous();
  
  /**
   * Skip part of the song.
   * 
   * @param delta
   */
  void skip(long delta);
  
  /**
   * Get the current playing song.
   * 
   * @return
   */
  String getCurrent();
  
  /**
   * Get the volume level.
   * 
   * @return
   */
  int getVolume();
  
  /**
   * Change the volume level by the given amount.
   * 
   * @param amount
   */
  void changeVolume(int amount);
  
  /**
   * Set the volume level to the given amount.
   * 
   * @param amount
   */
  void setVolume(int amount);
  
  /**
   * Check if the player is playing.
   * 
   * @return
   */
  boolean isPlaying();

  /**
   * Play the song at the given position in the playlist.
   * 
   * @param parseInt
   */
  void playAtPosition(int parseInt);
}
