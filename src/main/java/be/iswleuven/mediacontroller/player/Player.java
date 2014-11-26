package be.iswleuven.mediacontroller.player;

import java.util.Observer;

public abstract class Player implements Observer {

  /**
   * Flag to indicate if the player is currently playing.
   */
  private boolean playing;
  
  /**
   * Flag to indicate if the player is currently paused.
   */
  private boolean paused;
  
  /**
   * The history instance.
   */
  private final History history;
  
  /**
   * The playlist of this player.
   */
  private final Playlist playlist;
  
  /**
   * The current playing song.
   */
  private Song currentSong;
  
  /**
   * Create a new player.
   * 
   * @param history
   * @param playlist
   */
  public Player(History history, Playlist playlist) {
    this.history = history;
    this.playlist = playlist;
    this.playlist.addObserver(this);
  }
  
  /**
   * Check if the player is playing.
   * 
   * @return
   */
  public boolean isPlaying() {
    return this.playing;
  }
  
  /**
   * Set the playing flag.
   * 
   * @param playing
   */
  public void setPlaying(boolean playing) {
    this.playing = playing;
  }
  
  /**
   * Check if the player is paused.
   * 
   * @return
   */
  public boolean isPaused() {
    return this.paused;
  }
  
  /**
   * Set the paused flag.
   * 
   * @param paused
   */
  public void setPaused(boolean paused) {
    this.paused = paused;
  }
  
  /**
   * Get the playlist of this player.
   * 
   * @return
   */
  public Playlist getPlaylist() {
    return this.playlist;
  }
  
  /**
   * Get the current playing song.
   * 
   * @return
   */
  public Song getCurrentSong() {
    return this.currentSong;
  }
  
  /**
   * Set the current playing song.
   */
  public void setCurrentSong(Song song) {
    this.currentSong = song;
  }
  
  /**
   * Get the player history.
   * 
   * @return
   */
  public History getHistory() {
    return this.history;
  }
  
  /**
   * Start playing.
   */
  public abstract void play();
  
  /**
   * Pause playing.
   */
  public abstract void pause();
  
  /**
   * Stop playing.
   */
  public abstract void stop();
  
  /**
   * Skip part of the song.
   * 
   * @param delta
   */
  public abstract void skip(long delta);
  
  /**
   * Get the current playing song title.
   * 
   * @return
   */
  public abstract String getCurrentlyPlaying();
  
  /**
   * Get the volume level.
   * 
   * @return
   */
  public abstract int getVolume();
  
  /**
   * Change the volume level by the given amount.
   * 
   * @param amount
   */
  public abstract void changeVolume(int amount);
  
  /**
   * Set the volume level to the given amount.
   * 
   * @param amount
   */
  public abstract void setVolume(int amount);
  
}
