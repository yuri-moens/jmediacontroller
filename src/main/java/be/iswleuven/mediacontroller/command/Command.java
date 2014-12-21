package be.iswleuven.mediacontroller.command;

import be.iswleuven.mediacontroller.plugin.Plugin;
import be.iswleuven.mediacontroller.server.Worker;

public abstract class Command {
  
  /**
   * The parameters array.
   */
  protected String[] parameters;
  
  /**
   * The output message of the command.
   */
  protected String message;
  
  /**
   * The worker calling the command.
   */
  protected Worker worker;
  
  /**
   * The plugin associated with the command.
   */
  protected final Plugin plugin;
  
  /**
   * Create a new command.
   * 
   * @param plugin
   */
  public Command(Plugin plugin) {
    this.plugin = plugin;
  }
  
  /**
   * Get the parameters.
   * 
   * @return
   */
  public String[] getParameters() {
    return this.parameters;
  }
  
  /**
   * Set the parameters.
   * 
   * @param parameters
   */
  public void setParameters(String[] parameters) {
    this.parameters = parameters;
  }
  
  /**
   * Get the output message.
   * 
   * @return
   */
  public String getMessage() {
    return this.message;
  }
  
  /**
   * Set the output message and notify the worker.
   * 
   * @param message
   */
  public void setMessage(String message) {
    setMessage(message, true);
  }
  
  /**
   * Set the output message and notify the worker if requested.
   * 
   * @param message
   * @param notify
   */
  public void setMessage(String message, boolean notify) {
    this.message = message;
    
    if (notify) {
      notifyWorker();
    }
  }
  
  /**
   * Return the worker.
   * 
   * @return
   */
  public Worker getWorker() {
    return this.worker;
  }
  
  /**
   * Register a worker to the command.
   * 
   * @param worker
   */
  public void registerWorker(Worker worker) {
    this.worker = worker;
  }
  
  /**
   * Notify the worker and pass a callback to the command.
   */
  public void notifyWorker() {
   this.worker.notify(this);
  }
  
  /**
   * Execute the command.
   */
  public abstract void execute() throws CommandException;
  
}
