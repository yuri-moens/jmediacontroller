package be.iswleuven.mediacontroller.command;

public class CommandNotFoundException extends CommandException {

  /**
   * Serial ID
   */
  private static final long serialVersionUID = 40171040445986851L;
  
  /**
   * Create a new command not found exception.
   */
  public CommandNotFoundException(String command) {
    super("Commando \"" + command + "\" niet gevonden.");
  }

}
