package be.iswleuven.mediacontroller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import be.iswleuven.mediacontroller.MediaController;
import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;

public class SocketServerWorker extends Worker {

  /**
   * The logger instance.
   */
  private final static Logger logger = Logger.getLogger(SocketServerWorker.class);

  /**
   * The welcome message.
   */
  private final static String WELCOME_MESSAGE = ""
      + "##########################################\n"
      + "#     Welkom bij de MediaController!     #\n"
      + "#                                        #\n"
      + "# Gebruik het 'help' commando om         #\n"
      + "# een lijst van commando's te zien.      #\n"
      + "#                                        #\n"
      + "##########################################";
  
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
    
    logger.log(Level.INFO, "Created socket server worker for client with address: " + clientSocket.getInetAddress().toString());
  }
  
  @Override
  public void run() {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      out = new PrintWriter(clientSocket.getOutputStream(), true);

      out.println(WELCOME_MESSAGE);
      
      out.print("mc@isw » ");
      out.flush();
  
      String input;
      while ((input = in.readLine()) != null ) {
        if (input.toLowerCase().equals("exit")) {
          this.clientSocket.close();
        }
        
        try {
          socketServer.getCommandBus().send(input, this);
        } catch (CommandException e) {
          out.println(e.getMessage());
        }
        out.print("mc@isw » ");
        out.flush();
      }
    } catch (IOException e) {
      
    } finally {
      setChanged();
      notifyObservers(this);
      
      if (MediaController.verbose) {
        System.out.println("Verbinding verbroken.");
      }
      
      logger.log(Level.INFO, "Closed connection with client with address: " + clientSocket.getInetAddress().toString());
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
