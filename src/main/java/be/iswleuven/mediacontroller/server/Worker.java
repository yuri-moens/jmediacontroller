package be.iswleuven.mediacontroller.server;

import java.net.InetAddress;

import be.iswleuven.mediacontroller.command.Command;

public interface Worker {

  /**
   * Return the client internet address.
   * 
   * @return
   */
  InetAddress getAddress();
  
  /**
   * Called when a command's state has changed.
   */
  void notify(Command command);
  
}
