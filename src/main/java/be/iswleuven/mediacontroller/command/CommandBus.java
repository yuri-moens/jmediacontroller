package be.iswleuven.mediacontroller.command;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import be.iswleuven.mediacontroller.server.Worker;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CommandBus extends Observable {

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
  @Inject
  public CommandBus(CommandFactory commandFactory) {
    commands = new LinkedList<Command>();
    this.commandFactory = commandFactory;
  }
  
  /**
   * Send the command to the command bus.
   * 
   * @param command
   * @param worker
   * @throws CommandException
   */
  public void send(String command, Worker worker) throws CommandException {
    command = command.toLowerCase();
    
    add(commandFactory.createCommand(command, worker));
  }
  
  /**
   * Add the command to the command bus.
   * 
   * @param command
   */
  public void add(Command command) {
    commands.add(command);
    
    setChanged();
    notifyObservers(this);
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
