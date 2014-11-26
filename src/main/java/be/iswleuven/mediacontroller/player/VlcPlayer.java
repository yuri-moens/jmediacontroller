package be.iswleuven.mediacontroller.player;

import java.util.Observable;
import java.util.Observer;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import be.iswleuven.mediacontroller.config.Config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

@Singleton
public class VlcPlayer extends MediaPlayerEventAdapter implements Observer, Player {
  
  /**
   * The playlist instance.
   */
  private final Playlist playlist;
  
  /**
   * The media player.
   */
  private final MediaPlayer player;
  
  /**
   * The currently playing song.
   */
  private Song currentSong;

  /**
   * Create a new VLC player.
   * 
   * @param config
   * @param playlist
   */
  @Inject
  public VlcPlayer(Config config, Playlist playlist) {
    this.playlist = playlist;
    this.playlist.addObserver(this);

    if (! config.getVlcLocation().equals("")) {
      NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), config.getVlcLocation());
    }
    
    Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
    
    this.player = new MediaPlayerFactory().newHeadlessMediaPlayer();
    this.player.addMediaPlayerEventListener(this);
  }
  
  @Override
  public void play() {
    this.player.playMedia(this.currentSong.getUrl());
  }

  @Override
  public void pause() {
    this.player.pause();
  }

  @Override
  public void stop() {
    this.playlist.clear();
    this.currentSong = null;
    this.player.stop();
  }

  @Override
  public void next() {
    this.currentSong = this.playlist.getNext();
    this.player.stop();
  }
  
  @Override
  public void previous() {
    this.currentSong = this.playlist.getPrevious();
    this.player.stop();
  }
  
  @Override
  public void playAtPosition(int position) {
    this.currentSong = this.playlist.getAtPosition(position);
    this.player.stop();
  }

  @Override
  public void skip(long delta) {
    this.player.skip(delta);
  }

  @Override
  public String getCurrent() {
    if (isPlaying()) {
      if (this.player.getMediaMeta().getNowPlaying() == null) {
        return this.currentSong.getTitle();
      } else {
        return this.player.getMediaMeta().getNowPlaying();
      }
    } else {
      return "Niet aan het spelen";
    }
  }

  @Override
  public int getVolume() {
    return this.player.getVolume();
  }

  @Override
  public void changeVolume(int amount) {
    setVolume(this.player.getVolume() + amount);
  }

  @Override
  public void setVolume(int amount) {
    amount = Math.abs(amount) > 100 ? 100 : Math.abs(amount);
    
    this.player.setVolume(amount);
  }
  
  @Override
  public boolean isPlaying() {
    return this.player.isPlaying();
  }

  @Override
  public void update(Observable obs, Object o) {
    if (! isPlaying()) {
      this.currentSong = this.playlist.getNext();
      play();
    }
  }
  
  @Override
  public void finished(MediaPlayer player) {
    this.currentSong = null;
    if (! this.playlist.isEmpty()) {
      this.currentSong = this.playlist.getNext();
      this.play(); 
    }
  }
  
  @Override
  public void stopped(MediaPlayer player) {
    if (this.currentSong != null) {
      this.play();
    }
  }
  
  @Override
  public void mediaMetaChanged(MediaPlayer player, int metaType) {
    player.parseMedia();
  }
  
}
