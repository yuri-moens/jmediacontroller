package be.iswleuven.mediacontroller.command;

import java.util.Observable;
import java.util.Observer;

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
    
    this.commandBus.addObserver(this);
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
  public void update(Observable obs, Object o) {
    Command command = commandBus.getCommands().poll();
    
    command.execute();
  }
}
