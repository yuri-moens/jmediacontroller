package be.iswleuven.mediacontroller.player;

import java.util.Observable;
import java.util.Observer;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.AudioMediaListPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;

public class VlcPlayer implements Observer, Player {

  /**
   * The VLC player instance.
   */
  private static VlcPlayer vlcPlayer;
  
  /**
   * The media player component instance.
   */
  private final AudioMediaListPlayerComponent playerComponent;
  
  /**
   * Create a new VLC player.
   */
  private VlcPlayer() {
    Playlist.getInstance().addObserver(this);

    Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
    
    playerComponent = new AudioMediaListPlayerComponent();
  }
  
  /**
   * Get the VLC player instance.
   * 
   * @return
   */
  public static VlcPlayer getInstance() {
    if (VlcPlayer.vlcPlayer == null) {
      VlcPlayer.vlcPlayer = new VlcPlayer();
    }
    
    return VlcPlayer.vlcPlayer;
  }
  
  @Override
  public void play() {
    this.playerComponent.getMediaPlayer().play();
  }

  @Override
  public void pause() {
    this.playerComponent.getMediaPlayer().pause();
  }

  @Override
  public void stop() {
    this.playerComponent.getMediaPlayer().stop();
  }

  @Override
  public void next() {
    this.playerComponent.getMediaListPlayer().playNext();
  }

  @Override
  public void previous() {
    this.playerComponent.getMediaListPlayer().playPrevious();
  }

  @Override
  public void skip(long delta) {
    this.playerComponent.getMediaPlayer().skip(delta);
  }

  @Override
  public void changeVolume(int amount) {
    setVolume(this.playerComponent.getMediaPlayer().getVolume() + amount);
  }

  @Override
  public void setVolume(int amount) {
    amount = Math.abs(amount) % 101;
    
    this.playerComponent.getMediaPlayer().setVolume(amount);
  }

  @Override
  public void update(Observable obs, Object o) {    
    Playlist playlist = (Playlist) o;
    
    this.playerComponent.getMediaList().addMedia(playlist.getSong().getUrl());
    
    if (! this.playerComponent.getMediaPlayer().isPlaying()) {
      play();
    }
  }
  
}
