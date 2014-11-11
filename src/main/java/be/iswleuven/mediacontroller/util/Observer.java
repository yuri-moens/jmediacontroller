package be.iswleuven.mediacontroller.util;

public interface Observer {

  /**
   * Update when something changes in the observable object.
   */
  void update(Observable obs, String message);
  
}
