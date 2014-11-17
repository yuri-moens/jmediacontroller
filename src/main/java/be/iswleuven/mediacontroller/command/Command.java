package be.iswleuven.mediacontroller.command;

import be.iswleuven.mediacontroller.util.Observable;

public abstract class Command extends Observable {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = null;
  
  /**
   * The number of allowed parameters.
   */
  public static final int NUMBER_OF_PARAMS = 0;
  
  /**
   * The parameters array.
   */
  protected String[] parameters;
  
  /**
   * Set the parameters.
   * 
   * @param parameters
   */
  public void setParameters(String[] parameters) throws InvalidParametersException {
    if (parameters.length != NUMBER_OF_PARAMS) {
      throw new InvalidParametersException(parameters.length, NUMBER_OF_PARAMS);
    }
    
    this.parameters = parameters;
  }
  
  /**
   * Execute the command.
   */
  public abstract void execute();
  
}
