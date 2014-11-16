package be.iswleuven.mediacontroller.server;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import be.iswleuven.mediacontroller.MediaController;
import be.iswleuven.mediacontroller.command.CommandBus;

public class ServerHandler {

  /**
   * The server handler instance.
   */
  private static ServerHandler serverHandler;

  /**
   * Map with the servers and their name.
   */
  private Map<String, Server> servers;
  
  /**
   * Create a new server handler.
   */
  private ServerHandler() {
    servers = new HashMap<String, Server>();
    addServers(MediaController.config.getServers());
  }
  
  /**
   * Get the instance of the server handler. Create it first if it doesn't exist yet.
   * 
   * @return
   */
  public static ServerHandler getInstance() {
    if (serverHandler == null) {
      serverHandler = new ServerHandler();
    }
    
    return serverHandler;
  }
  
  /**
   * Add the servers to the servers map.
   * 
   * @param servers
   */
  public void addServers(String[] servers) {
    CommandBus commandBus = CommandBus.getInstance();

    for (String server : servers) {
      try {
        String name = server.split(":")[0];
        int port = Integer.parseInt(server.split(":")[1]);
        
        Class<?> serverClass = Class.forName("be.iswleuven.mediacontroller.server." + name);
        
        Class<?>[] types = {CommandBus.class, Integer.TYPE};
        Constructor<?> constructor = serverClass.getConstructor(types);
        
        Server serverInstance = (Server) constructor.newInstance(commandBus, port);
        
        this.servers.put(name, serverInstance);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * Start all the servers.
   */
  public void startAllServers() {
    for (Server server : servers.values()) {
      startServer(server);
    }
  }
  
  /**
   * Start the server with the given name.
   * 
   * @param name
   */
  public void startServer(String name) {
    startServer(servers.get(name));
  }
  
  /**
   * Start the given server.
   * 
   * @param server
   */
  public void startServer(Server server) {
    new Thread(server).start();
  }
  
  /**
   * Stop all servers.
   */
  public void stopAllServers() {
    for (Server server : servers.values()) {
      stopServer(server);
    }
  }
  
  /**
   * Stop the server with the given name.
   * 
   * @param name
   */
  public void stopServer(String name) {
    stopServer(servers.get(name));
  }
  
  /**
   * Stop the given server.
   * 
   * @param server
   */
  public void stopServer(Server server) {
    server.stop();
  }
  
}
