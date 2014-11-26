package be.iswleuven.mediacontroller.player;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Observable;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Playlist extends Observable {
  
  /**
   * The songs queue.
   */
  private Deque<Song> songs;
  
  /**
   * The history of the playlist.
   */
  private History history;
  
  /**
   * Create a new playlist.
   */
  @Inject
  public Playlist(History history) {
    this.history = history;
    songs = new LinkedList<Song>();
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
  public Song getSong() {
    Song song = this.songs.poll();
    
    history.add(song);
    
    return song;
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
    for (int i = 0; i < amount; i++) {
      this.songs.removeLast();      
    }
  }
  
}
