package be.iswleuven.mediacontroller.server;

import be.iswleuven.mediacontroller.command.CommandBus;

public abstract class Server implements Runnable {
  
  /**
   * The port number.
   */
  private int port;

  /**
   * The command bus.
   */
  private CommandBus commandBus;
  
  /**
   * Specify if the server is stopped.
   */
  private boolean isStopped;
  
  /**
   * Create a server.
   * 
   * @param commandBus
   */
  public Server(CommandBus commandBus, int port) {
    this.commandBus = commandBus;
    this.port = port;
  }
  
  /**
   * Get the command bus.
   * 
   * @return
   */
  public CommandBus getCommandBus() {
    return commandBus;
  }
  
  /**
   * Get the port number.
   * 
   * @return
   */
  public int getPort() {
    return port;
  }
  
  /**
   * Stop the server.
   */
  public synchronized void stop() {
    isStopped = true;
  }
  
  /**
   * Check if the server is stopped.
   * 
   * @return
   */
  public synchronized boolean isStopped() {
    return isStopped;
  }
  
}
