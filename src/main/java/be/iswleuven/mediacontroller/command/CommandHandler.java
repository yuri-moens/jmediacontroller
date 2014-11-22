package be.iswleuven.mediacontroller.command;

import java.util.Observable;
import java.util.Observer;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CommandHandler implements Observer {
  
  /**
   * The instance of the command bus.
   */
  private CommandBus commandBus;
  
  /**
   * Create a new command handler.
   * 
   * @param commandBus
   */
  @Inject
  public CommandHandler(CommandBus commandBus) {
    this.commandBus = commandBus;
    
    this.commandBus.addObserver(this);
  }

  @Override
  public void update(Observable obs, Object o) {
    Command command = commandBus.getCommands().poll();
    
    try {
      command.execute();
    } catch (CommandException e) {
      command.setMessage(e.getMessage());
      command.notifyWorker();
    } catch (ArrayIndexOutOfBoundsException ee) {
      command.setMessage("Er werd een foute hoeveelheid parameters opgegeven.");
      command.notifyWorker();
    }
  }
}
