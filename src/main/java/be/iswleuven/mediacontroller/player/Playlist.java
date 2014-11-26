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
   * The playlist history.
   */
  private final History history;
  
  /**
   * The number that points to the index of the current playing file.
   */
  private int position = -1;
  
  /**
   * Create a new playlist.
   */
  @Inject
  public Playlist(Config config, History history) {
    this.MAX_HISTORY_SIZE = config.getMaxHistorySize();
    this.history = history;
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
   * Get the next song.
   * 
   * @return
   */
  public Song getNext() {
    Song song = null;
    
    if (this.position == this.MAX_HISTORY_SIZE) {
      this.songs.remove(0);
      
      song = this.songs.get(this.position);
    } else {
      song = this.songs.get(++this.position);
    }
    
    this.history.add(song);
    
    return song;
  }
  
  /**
   * Get the previous song.
   * 
   * @return
   */
  public Song getPrevious() {
    Song song = null;
    
    if (this.position != 0) {
      song = this.songs.get(--this.position);
    }
    
    this.history.add(song);
    
    return song;
  }
  
  /**
   * Get the song at the given position.
   * 
   * @param position
   * @return
   */
  public Song getAtPosition(int position) {    
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
    
    return this.songs.get(this.position);
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
