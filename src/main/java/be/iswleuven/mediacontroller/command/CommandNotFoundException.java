package be.iswleuven.mediacontroller.command;

public class CommandNotFoundException extends Exception{

  /**
   * Serial ID
   */
  private static final long serialVersionUID = 40171040445986851L;
  
  /**
   * Create a new command not found exception.
   */
  public CommandNotFoundException(String command) {
    super("Command \"" + command + "\" not found.");
  }

}
