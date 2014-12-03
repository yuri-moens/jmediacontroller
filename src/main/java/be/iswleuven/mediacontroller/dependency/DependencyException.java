package be.iswleuven.mediacontroller.dependency;

public class DependencyException extends Exception {

  /**
   * The serial id.
   */
  private static final long serialVersionUID = -883988950136521401L;
  
  /**
   * Create a new dependency exception.
   * 
   * @param msg
   */
  public DependencyException(String msg) {
    super(msg);
  }

}
