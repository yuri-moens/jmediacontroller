package be.iswleuven.mediacontroller.player;

import java.util.ArrayList;
import java.util.Observable;

import be.iswleuven.mediacontroller.config.Config;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Playlist extends Observable {
  
  /**
   * The maximum history size.
   */
  private final int MAX_HISTORY_SIZE;
  
  /**
   * The songs queue.
   */
  private ArrayList<Song> songs;
  
  /**
   * The number that points to the index of the current playing song.
   */
  private int position = 0;
  
  /**
   * Flag indicating if the current song should be skipped.
   */
  private boolean skipCurrent;
  
  /**
   * Create a new playlist.
   */
  @Inject
  public Playlist(Config config) {
    this.MAX_HISTORY_SIZE = config.getMaxHistorySize();
    this.songs = new ArrayList<Song>();
  }
  
  /**
   * Add a song to the playlist.
   * 
   * @param song
   */
  public void addSong(Song song) {
    this.songs.add(song);
    
    setChanged();
    notifyObservers(this);
  }
  
  /**
   * Get the song at the current position.
   * 
   * @return
   */
  public Song getSong() {
    if (this.songs.size() == this.position) {
      this.position--;

      return null;
    } else {
      return this.songs.get(this.position);
    }
  }
  
  /**
   * Set the playlist position.
   * 
   * @param position
   */
  public void setPosition(int position) {
    if (position >= 0 && position < this.songs.size()) {
      if (position > this.MAX_HISTORY_SIZE) {
        int delta = position - this.MAX_HISTORY_SIZE;
        
        for (int i = 0; i < delta; i++) {
          this.songs.remove(0);
        }
        
        this.position = this.MAX_HISTORY_SIZE;
      } else {
        this.position = position;
      }
    }
    
    setChanged();
    notifyObservers(this);
  }
  
  /**
   * Set the position to the next song.
   */
  public void nextSong() {
    if (this.position == this.MAX_HISTORY_SIZE) {
      this.songs.remove(0);
    } else {
      this.position++;
    }
    
    setChanged();
    notifyObservers(this);
  }

  /**
   * Set the position to the previous song.
   */
  public void previousSong() {
    if (this.position != 0) {
      this.position--;
    }
    
    setChanged();
    notifyObservers(this);
  }
  
  /**
   * Return true if the current playing song should be skipped.
   * 
   * @return
   */
  public boolean shouldSkipCurrent() {
    return this.skipCurrent;
  }
  
  /**
   * Toggle the skip current flag.
   */
  public void toggleSkipCurrent() {
    this.skipCurrent = !this.skipCurrent;
  }
  
  /**
   * Check if the songs list is empty.
   * 
   * @return
   */
  public boolean isEmpty() {
    return this.songs.isEmpty();
  }
  
  /**
   * Clear the songs list.
   */
  public void clear() {
    this.songs.clear();
    this.position = 0;
  }
  
  /**
   * Remove the last song.
   */
  public void removeLast() {
    removeLast(1);
  }
  
  /**
   * Remove the last amount of songs.
   * 
   * @param amount
   */
  public void removeLast(int amount) {
    for (int i = 0; i < amount && this.songs.size() > 0; i++) {
      this.songs.remove(this.songs.size() - 1);     
    }
  }
  
}
