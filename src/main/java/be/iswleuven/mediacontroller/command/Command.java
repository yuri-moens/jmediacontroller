package be.iswleuven.mediacontroller.command;

import be.iswleuven.mediacontroller.util.Observable;

public abstract class Command extends Observable {

  /**
   * The command string.
   */
  private String commandString;
  
  /**
   * The number of parameters.
   */
  private int numberOfParams;
  
  /**
   * Create a new command.
   * 
   * @param commandString
   */
  public Command(String commandString) {
    this(commandString, 0);
  }
  
  /**
   * Create a new command.
   * 
   * @param commandString
   * @param numberOfParams
   */
  public Command(String commandString, int numberOfParams) {
    this.commandString = commandString;
    this.numberOfParams = numberOfParams;
  }
  
  /**
   * Get the command string.
   * 
   * @return
   */
  public String getCommandString() {
    return commandString;
  }
  
  /**
   * Get the number of parameters.
   * 
   * @return
   */
  public int getNumberOfParams() {
    return numberOfParams;
  }
  
  /**
   * Execute the command.
   */
  public abstract void execute();
  
}
