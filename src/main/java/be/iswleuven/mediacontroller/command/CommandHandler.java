package be.iswleuven.mediacontroller.command;

import java.util.Observable;
import java.util.Observer;

import be.iswleuven.mediacontroller.admin.AdminHandler;
import be.iswleuven.mediacontroller.server.Worker;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CommandHandler implements Observer {
  
  /**
   * The admin handler instance.
   */
  private final AdminHandler adminHandler;
  
  /**
   * The instance of the command bus.
   */
  private CommandBus commandBus;
  
  /**
   * Flag to block commands from executing.
   */
  private boolean blocked = false;
  
  /**
   * The worker that is currently blocking.
   */
  private Worker blockingWorker = null;
  
  /**
   * Create a new command handler.
   * 
   * @param commandBus
   */
  @Inject
  public CommandHandler(AdminHandler adminHandler, CommandBus commandBus) {
    this.adminHandler = adminHandler;
    this.commandBus = commandBus;
    
    this.commandBus.addObserver(this);
  }

  /**
   * Check if the executing of commands is blocked.
   * 
   * @return
   */
  public boolean isBlocked() {
    return this.blocked;
  }
  
  /**
   * Toggle the block on the executing of commands.
   * 
   * @param worker
   * @return
   */
  public boolean block(Worker worker) {
    this.blocked = !this.blocked;
    
    if (blocked) {
      this.blockingWorker = worker;
    } else {
      this.blockingWorker = null;
    }
    
    return this.blocked;
  }

  @Override
  public void update(Observable obs, Object o) {
    Command command = commandBus.getCommands().poll();
    
    try {
      if (blocked
          && command.getWorker() != blockingWorker
          && !command.PLUGIN.getName().equals("AdminPlugin")
          && !adminHandler.isAdmin(command.getWorker())) {
        command.setMessage("De mediacontroller werd geblokkeerd door " + blockingWorker.getAddress());
        command.notifyWorker();
      } else {
        command.execute();
      }
    } catch (CommandException e) {
      command.setMessage(e.getMessage());
      command.notifyWorker();
    } catch (ArrayIndexOutOfBoundsException ee) {
      command.setMessage("Er werd een foute hoeveelheid parameters opgegeven.");
      command.notifyWorker();
    }
  }
}
