package be.iswleuven.mediacontroller.command;

public class CommandException extends Exception {

  /**
   * The serial ID.
   */
  private static final long serialVersionUID = -9172982506171056771L;

  /**
   * Create a new CommandException.
   * 
   * @param msg
   */
  public CommandException(String msg) {
    super(msg);
  }
  
}
