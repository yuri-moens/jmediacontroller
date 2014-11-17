package be.iswleuven.mediacontroller.command;

import java.util.LinkedList;
import java.util.Queue;

import be.iswleuven.mediacontroller.util.Observable;
import be.iswleuven.mediacontroller.util.Observer;

public class CommandBus extends Observable {
  
  /**
   * The instance of the command bus.
   */
  private static CommandBus commandBus;

  /**
   * The commands queue.
   */
  private Queue<Command> commands;
  
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
   * Send the command to the command bus.
   * 
   * @param command
   * @param obs
   * @throws CommandException
   */
  public void send(String command, Observer obs) throws CommandException {
    add(commandFactory.createCommand(command, obs));
  }
  
  /**
   * Add the command to the command bus.
   * 
   * @param command
   */
  public void add(Command command) {
    commands.add(command);
    
    notifyObservers("Command added");
  }
  
  /**
   * Get the commands waiting to be executed.
   * 
   * @return
   */
  public Queue<Command> getCommands() {
    return commands;
  }
  
}
