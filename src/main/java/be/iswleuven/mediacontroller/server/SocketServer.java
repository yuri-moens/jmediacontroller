package be.iswleuven.mediacontroller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import be.iswleuven.mediacontroller.command.CommandBus;

public class SocketServer extends Server {
  
  /**
   * Create a simple socket server.
   * 
   * @param commandBus
   * @param port
   */
  public SocketServer(CommandBus commandBus, int port) {
    super(commandBus, port);
  }

  @Override
  public void run() {    
    try {
      ServerSocket serverSocket = new ServerSocket(getPort());
      
      while (! isStopped()) {
        Socket clientSocket = null;
        
        clientSocket = serverSocket.accept();
        
        new Thread(new SocketServerWorker(clientSocket, this)).start();
      }
      
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
