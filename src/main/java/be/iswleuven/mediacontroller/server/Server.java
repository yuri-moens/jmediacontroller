package be.iswleuven.mediacontroller.server;

import be.iswleuven.mediacontroller.command.CommandBus;

public abstract class Server implements Runnable {
  
  /**
   * The port number.
   */
  private int port;

  /**
   * The name of the server.
   */
  private String name;
  
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
  public Server(String name, CommandBus commandBus, int port) {
    this.name = name;
    this.commandBus = commandBus;
    this.port = port;
  }
  
  /**
   * Get the command bus.
   * 
   * @return
   */
  public CommandBus getCommandBus() {
    return this.commandBus;
  }
  
  /**
   * Get the port number.
   * 
   * @return
   */
  public int getPort() {
    return this.port;
  }
  
  /**
   * Get the name of the server.
   * 
   * @return
   */
  public String getName() {
    return this.name;
  }
  
  /**
   * Stop the server.
   */
  public synchronized void stop() {
    this.isStopped = true;
  }
  
  /**
   * Check if the server is stopped.
   * 
   * @return
   */
  public synchronized boolean isStopped() {
    return this.isStopped;
  }
  
}
