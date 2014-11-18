package be.iswleuven.mediacontroller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import be.iswleuven.mediacontroller.MediaController;
import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;

public class SocketServerWorker implements Runnable, Worker {

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
        try {
          socketServer.getCommandBus().send(input, this);
        } catch (CommandException e) {
          out.println(e.getMessage());
        }
      }
    } catch (IOException e) {
      if (MediaController.verbose) {
        System.out.println("Verbinding verbroken.");
      }
    }
  }

  @Override
  public InetAddress getAddress() {
    return this.clientSocket.getInetAddress();
  }

  @Override
  public void notify(Command command) {
    this.out.println(command.getMessage());
  }

}
