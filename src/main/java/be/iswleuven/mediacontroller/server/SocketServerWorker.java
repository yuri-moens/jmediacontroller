package be.iswleuven.mediacontroller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import be.iswleuven.mediacontroller.util.Observable;
import be.iswleuven.mediacontroller.util.Observer;

public class SocketServerWorker implements Runnable, Observer {

  /**
   * The client socket.
   */
  private Socket clientSocket;
  
  /**
   * The socket server.
   */
  private SocketServer socketServer;
  
  /**
   * The socket output stream.
   */
  private PrintWriter out;
  
  /**
   * Create a new worker.
   * 
   * @param clientSocket
   */
  public SocketServerWorker(Socket clientSocket, SocketServer socketServer) {
    this.clientSocket = clientSocket;
    this.socketServer = socketServer;
  }
  
  @Override
  public void run() {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      out = new PrintWriter(clientSocket.getOutputStream(), true);
  
      String input;
      while ((input = in.readLine()) != null ) {
        socketServer.getCommandBus().add(input, this);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(Observable obs, String message) {
    out.println(message);
  }

}
