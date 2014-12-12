package be.iswleuven.mediacontroller.server;

import java.net.InetAddress;
import java.util.Observable;

import be.iswleuven.mediacontroller.command.Command;

public abstract class Worker extends Observable implements Runnable {

  /**
   * Return the client internet address.
   * 
   * @return
   */
  public abstract InetAddress getAddress();
  
  /**
   * Called when a command's state has changed.
   */
  public abstract void notify(Command command);
  
}
