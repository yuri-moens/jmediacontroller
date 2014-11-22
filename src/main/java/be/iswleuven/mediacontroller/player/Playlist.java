package be.iswleuven.mediacontroller.player;

import java.util.LinkedList;
import java.util.Observable;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Playlist extends Observable {
  
  /**
   * The songs queue.
   */
  private LinkedList<Song> songs;
  
  /**
   * Create a new playlist.
   */
  @Inject
  public Playlist() {
    songs = new LinkedList<Song>();
  }
  
  /**
   * Get the songs in the playlist.
   * 
   * @return
   */
  public LinkedList<Song> getSongs() {
    return this.songs;
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
   * Add the contents of a different playlist to this playlist.
   * 
   * @param playlist
   */
  public void addPlaylist(Playlist playlist) {
    this.songs.addAll(playlist.getSongs());
    
    setChanged();
    notifyObservers(this);
  }
  
  /**
   * Get the next song.
   * 
   * @return
   */
  public Song getSong() {
    return this.songs.poll();
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
