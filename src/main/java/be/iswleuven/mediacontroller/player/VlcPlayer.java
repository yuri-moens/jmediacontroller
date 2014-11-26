package be.iswleuven.mediacontroller.player;

import java.util.Observable;
import java.util.Observer;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import be.iswleuven.mediacontroller.config.Config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

@Singleton
public class VlcPlayer extends Player implements MediaPlayerEventListener, Observer {
  
  /**
   * The media player.
   */
  private final MediaPlayer player;

  /**
   * Create a new VLC player.
   * 
   * @param config
   * @param playlist
   */
  @Inject
  public VlcPlayer(Config config, History history, Playlist playlist) {
    super(history, playlist);

    if (! config.getVlcLocation().equals("")) {
      NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), config.getVlcLocation());
    }
    
    Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
    
    this.player = new MediaPlayerFactory().newHeadlessMediaPlayer();
    this.player.addMediaPlayerEventListener(this);
  }
  
  @Override
  public void play() {    
    setCurrentSong(getPlaylist().getSong());
    
    if (getCurrentSong() != null) {
      setPlaying(true);
      setPaused(false);
      this.player.playMedia(getCurrentSong().getUrl());
      getHistory().add(getCurrentSong());
    }
  }

  @Override
  public void pause() {
    if (isPaused()) {
      setPaused(false);
    } else {
      setPaused(true);
    }
    
    this.player.pause();
  }

  @Override
  public void stop() {    
    this.player.stop();
  }

  @Override
  public void skip(long delta) {
    this.player.skip(delta);
  }

  @Override
  public String getCurrentlyPlaying() {
    if (isPlaying()) {
      if (this.player.getMediaMeta().getNowPlaying() == null) {
        return getCurrentSong().getTitle();
      } else {
        return this.player.getMediaMeta().getNowPlaying();
      }
    } else {
      return "Niets aan het spelen";
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
  public void update(Observable obs, Object o) {
    if (! isPlaying()) {
      play();
    } else if (getPlaylist().shouldSkipCurrent()) {
      getPlaylist().toggleSkipCurrent();
      play();
    }
  }
  
  @Override
  public void finished(MediaPlayer player) {
    setPlaying(false);
    setPaused(false);
    setCurrentSong(null);
    
    getPlaylist().nextSong();
  }
  
  @Override
  public void stopped(MediaPlayer player) {
    setPlaying(false);
    setPaused(false);
    setCurrentSong(null);
  }

  @Override
  public void mediaMetaChanged(MediaPlayer mediaPlayer, int metaType) {
    this.player.parseMedia();
  }
  
  // Below  this line are empty event handlers. If you implement one, move it above this line.

  @Override
  public void mediaChanged(MediaPlayer mediaPlayer, libvlc_media_t media, String mrl) {
    
  }

  @Override
  public void opening(MediaPlayer mediaPlayer) {
    
  }

  @Override
  public void buffering(MediaPlayer mediaPlayer, float newCache) {
    
  }

  @Override
  public void playing(MediaPlayer mediaPlayer) {
    
  }

  @Override
  public void paused(MediaPlayer mediaPlayer) {
    
  }

  @Override
  public void forward(MediaPlayer mediaPlayer) {
    
  }

  @Override
  public void backward(MediaPlayer mediaPlayer) {
    
  }

  @Override
  public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
    
  }

  @Override
  public void positionChanged(MediaPlayer mediaPlayer, float newPosition) {
    
  }

  @Override
  public void seekableChanged(MediaPlayer mediaPlayer, int newSeekable) {
    
  }

  @Override
  public void pausableChanged(MediaPlayer mediaPlayer, int newPausable) {
    
  }

  @Override
  public void titleChanged(MediaPlayer mediaPlayer, int newTitle) {
    
  }

  @Override
  public void snapshotTaken(MediaPlayer mediaPlayer, String filename) {
    
  }

  @Override
  public void lengthChanged(MediaPlayer mediaPlayer, long newLength) {
    
  }

  @Override
  public void videoOutput(MediaPlayer mediaPlayer, int newCount) {
    
  }

  @Override
  public void error(MediaPlayer mediaPlayer) {
    
  }

  @Override
  public void mediaSubItemAdded(MediaPlayer mediaPlayer, libvlc_media_t subItem) {
    
  }

  @Override
  public void mediaDurationChanged(MediaPlayer mediaPlayer, long newDuration) {
    
  }

  @Override
  public void mediaParsedChanged(MediaPlayer mediaPlayer, int newStatus) {
    
  }

  @Override
  public void mediaFreed(MediaPlayer mediaPlayer) {
    
  }

  @Override
  public void mediaStateChanged(MediaPlayer mediaPlayer, int newState) {
    
  }

  @Override
  public void newMedia(MediaPlayer mediaPlayer) {
    
  }

  @Override
  public void subItemPlayed(MediaPlayer mediaPlayer, int subItemIndex) {
    
  }

  @Override
  public void subItemFinished(MediaPlayer mediaPlayer, int subItemIndex) {
    
  }

  @Override
  public void endOfSubItems(MediaPlayer mediaPlayer) {
    
  }
  
}
