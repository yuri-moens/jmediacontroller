package be.iswleuven.mediacontroller.command;

public class InvalidParametersException extends CommandException {

  /**
   * The serial ID.
   */
  private static final long serialVersionUID = 2718851551714678876L;

  /**
   * Create a new InvalidParametersException.
   * 
   * @param given
   * @param expected
   */
  public InvalidParametersException(int given, int expected) {
    super("Er werd een foute hoeveelheid parameters opgegeven. (" + given + " gegeven, " + expected + " verwacht)");
  }
  
}
