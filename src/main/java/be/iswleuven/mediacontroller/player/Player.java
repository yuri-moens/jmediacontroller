package be.iswleuven.mediacontroller.player;

public interface Player {

  void play();
  
  void pause();
  
  void stop();
  
  void skip();
  
  void changeVolume(int amount);
  
  void setVolume(int amount);
  
}
