package be.iswleuven.mediacontroller.player;

import java.util.Observable;
import java.util.Observer;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;

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
   * Create a new VLC player.
   */
  public VlcPlayer() {
    playlist = Playlist.getInstance();
    playlist.addObserver(this);

    Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
    
    this.player = new MediaPlayerFactory().newHeadlessMediaPlayer();
  }
  
  @Override
  public void play() {
    this.player.playMedia(this.playlist.getSong().getUrl());
    this.player.addMediaPlayerEventListener(this);
  }

  @Override
  public void pause() {
    this.player.pause();
  }

  @Override
  public void stop() {
    this.playlist.clear();
    this.player.stop();
  }

  @Override
  public void next() {
    this.player.stop();
  }

  @Override
  public void skip(long delta) {
    this.player.skip(delta);
  }

  @Override
  public void changeVolume(int amount) {
    setVolume(this.player.getVolume() + amount);
  }

  @Override
  public void setVolume(int amount) {
    amount = Math.abs(amount) % 101;
    
    this.player.setVolume(amount);
  }
  
  @Override
  public boolean isPlaying() {
    return this.player.isPlaying();
  }

  @Override
  public void update(Observable obs, Object o) {
    if (! isPlaying()) {
      play();
    }
  }
  
  @Override
  public void finished(MediaPlayer player) {
    if (! this.playlist.isEmpty()) {
      this.play(); 
    }
  }
  
}
