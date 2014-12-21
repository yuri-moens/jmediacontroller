package be.iswleuven.mediacontroller.player;

public class InvalidPlayerListenerException extends Exception {

  /**
   * The serial id.
   */
  private static final long serialVersionUID = 1567773372096743L;
  
  /**
   * Create a new invalid player listener exception.
   * 
   * @param listenerInterface
   */
  public InvalidPlayerListenerException(String listenerInterface) {
    super("This object is not a valid listener for the current player. Must implement \"" + listenerInterface + "\"");
  }

}
