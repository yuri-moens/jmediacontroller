package be.iswleuven.mediacontroller.command;

import java.util.LinkedList;
import java.util.List;

import be.iswleuven.mediacontroller.util.Observer;

public class CommandBus {
  
  /**
   * The instance of the command bus.
   */
  private static CommandBus commandBus;

  /**
   * The commands list.
   */
  private List<Command> commands;
  
  /**
   * The command factory.
   */
  private CommandFactory commandFactory;
  
  /**
   * Create a new command bus.
   */
  private CommandBus() {
    commands = new LinkedList<Command>();
    commandFactory = new CommandFactory();
  }
  
  /**
   * Get the instance of the command bus. Create it first if it doesn't exist yet.
   * 
   * @return
   */
  public static CommandBus getInstance() {
    if (commandBus == null) {
      commandBus = new CommandBus();
    }
    
    return commandBus;
  }
  
  /**
   * Add the command to the command bus.
   * 
   * @param command
   * @param obs
   */
  public void add(String command, Observer obs) {
    add(commandFactory.createCommand(command, obs));
  }
  
  /**
   * Add the command to the command bus.
   * 
   * @param command
   */
  public void add(Command command) {
    commands.add(command);
  }
  
}
