package be.iswleuven.mediacontroller.player;

import java.util.LinkedList;
import java.util.Observable;

public class Playlist extends Observable {

  /**
   * The playlist instance.
   */
  private static Playlist playlist;
  
  /**
   * The songs queue.
   */
  private LinkedList<Song> songs;
  
  /**
   * Create a new playlist.
   */
  private Playlist() {
    songs = new LinkedList<Song>();
  }
  
  public static Playlist getInstance() {
    if (Playlist.playlist == null) {
      Playlist.playlist = new Playlist();
    }
    
    return Playlist.playlist;
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
  }
  
  /**
   * Add the contents of a different playlist to this playlist.
   * 
   * @param playlist
   */
  public void addPlaylist(Playlist playlist) {
    this.songs.addAll(playlist.getSongs());
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
