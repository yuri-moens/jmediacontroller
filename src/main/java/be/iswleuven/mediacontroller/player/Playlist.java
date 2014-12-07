package be.iswleuven.mediacontroller.player;

import java.util.ArrayList;
import java.util.List;
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
  private int position = -1;
  
  /**
   * Create a new playlist.
   */
  @Inject
  public Playlist(Config config) {
    this.MAX_HISTORY_SIZE = config.getMaxHistorySize();
    this.songs = new ArrayList<Song>();
  }
  
  /**
   * Get the current position.
   * 
   * @return
   */
  public int getPosition() {
    return this.position;
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
    return this.songs.get(this.position);
  }
  
  /**
   * Get the list with all the songs currently in the playlist.
   * 
   * @return
   */
  public List<Song> getSongs() {
    return this.songs;
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
  }
  
  /**
   * Set the position of the playlist to the last song.
   */
  public void setToLastPosition() {
    setPosition(this.songs.size() - 1);
  }
  
  /**
   * Set the position to the next song in the playlist if there is one.
   */
  public void nextSong() {
    if (this.position == this.songs.size() - 1) {
      return;
    }
    
    if (this.position == this.MAX_HISTORY_SIZE) {
      this.songs.remove(0);
    } else {
      this.position++;
    }
  }
  
  /**
   * Set the position to the previous osng in the playlist if there is one.
   */
  public void previousSong() {
    if (this.position != 0) {
      this.position--;
    }
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
    this.position = -1;
  }
  
  /**
   * Remove the last song and return it.
   */
  public Song removeLast() {
    if (this.songs.size() > 0 && this.position + 1 < this.songs.size()) {
      return this.songs.remove(this.songs.size() - 1);
    } else {
      return null;
    }
  }
  
}
