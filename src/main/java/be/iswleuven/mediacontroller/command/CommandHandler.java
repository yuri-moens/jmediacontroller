package be.iswleuven.mediacontroller.command;

import be.iswleuven.mediacontroller.util.Observable;
import be.iswleuven.mediacontroller.util.Observer;

public class CommandHandler implements Observer {

  /**
   * The instance of the command handler.
   */
  private static CommandHandler commandHandler;
  
  /**
   * The instance of the command bus.
   */
  private CommandBus commandBus;
  
  /**
   * Create a new command handler.
   * 
   * @param commandBus
   */
  private CommandHandler(CommandBus commandBus) {
    this.commandBus = commandBus;
  }
  
  /**
   * Get the command handler instance.
   * 
   * @return
   */
  public static CommandHandler getInstance() {
    if (commandHandler == null) {
      commandHandler = new CommandHandler(CommandBus.getInstance());
    }
    
    return commandHandler;
  }

  @Override
  public void update(Observable obs, String message) {
    Command command = commandBus.getCommands().poll();
    
    command.execute();
  }
}
