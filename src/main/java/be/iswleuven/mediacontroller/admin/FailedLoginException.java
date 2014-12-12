package be.iswleuven.mediacontroller.admin;

public class FailedLoginException extends Exception {

  /**
   * The serial id.
   */
  private static final long serialVersionUID = 9081068159765095780L;

  /**
   * Create a new failed login exception.
   * 
   * @param message
   */
  public FailedLoginException() {
    super("Verkeerd wachtwoord opgegeven.");
  }
  
}
