package be.iswleuven.mediacontroller.plugin.standard;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.command.CommandHandler;

import com.google.inject.Inject;

public class BlockCommand extends Command implements Observer {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "block";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "\t\t\tBlokkeer het uitvoeren van andere commando's voor andere gebruikers.";
  
  /**
   * The command handler instance.
   */
  private final CommandHandler commandHandler;
  
  /**
   * Create a new block command.
   * 
   * @param commandHandler
   * @param plugin
   */
  @Inject
  public BlockCommand(CommandHandler commandHandler, StandardPlugin plugin) {
    super(plugin);
    this.commandHandler = commandHandler;
  }

  @Override
  public void execute() throws CommandException {
    if (this.commandHandler.block(getWorker())) {
      setMessage("De mediacontroller werd geblokkeerd.");
      startTimeout();
      getWorker().addObserver(this);
    } else {
      setMessage("De mediacontroller werd gedeblokkeerd.");
    }
    
    notifyWorker();
  }

  @Override
  public void update(Observable obs, Object o) {
    unblock();
    
    obs.deleteObserver(this);
  }
  
  /**
   * Unblock the mediacontroller because of timeout or client exit.
   */
  private void unblock() {
    if (this.commandHandler.isBlocked()) {
      this.commandHandler.block(getWorker());
    }
  }
  
  /**
   * Start a timeout timer of 5 minutes so block cannot be abused too much.
   */
  private void startTimeout() {
    final long timeout = 5 * 60 * 1000;
    
    Timer timer = new Timer();
    
    timer.schedule(new TimeoutTimerTask(this), timeout);
  }
  
  class TimeoutTimerTask extends TimerTask {

    /**
     * The block command.
     */
    private final BlockCommand blockCommand;
    
    /**
     * Create a new timeout timer task.
     * 
     * @param blockCommand
     */
    public TimeoutTimerTask(BlockCommand blockCommand) {
      this.blockCommand = blockCommand;
    }
    
    @Override
    public void run() {
      blockCommand.unblock();
    }
    
  }

}
