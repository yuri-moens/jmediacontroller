package be.iswleuven.mediacontroller.command;

import be.iswleuven.mediacontroller.util.Observable;

public abstract class Command extends Observable {

  /**
   * The command string.
   */
  public static final String commandString = null;
  
  /**
   * Get the command string.
   * 
   * @return
   */
  public static String getCommandString() {
    return commandString;
  }
  
  /**
   * Execute the command.
   */
  public abstract void execute();
  
}
