package be.iswleuven.mediacontroller.server;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import be.iswleuven.mediacontroller.command.CommandBus;
import be.iswleuven.mediacontroller.config.Config;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ServerHandler {

  /**
   * Map with the servers and their name.
   */
  private Map<String, Server> servers;
  
  private CommandBus commandBus;
  
  /**
   * Create a new server handler.
   */
  @Inject
  public ServerHandler(CommandBus commandBus, Config config) {
    this.commandBus = commandBus;
    
    servers = new HashMap<String, Server>();
    
    addServers(config.getServers());
  }
  
  /**
   * Get the servers and their names.
   * 
   * @return
   */
  public Map<String, Server> getServers() {
    return this.servers;
  }
  
  /**
   * Add the servers to the servers map.
   * 
   * @param servers
   */
  public void addServers(String[] servers) {
    for (String server : servers) {
      try {
        String name = server.split(":")[0];
        int port = Integer.parseInt(server.split(":")[1]);
        
        Class<?> serverClass = Class.forName("be.iswleuven.mediacontroller.server." + name);
        
        Class<?>[] types = {CommandBus.class, Integer.TYPE};
        Constructor<?> constructor = serverClass.getConstructor(types);
        
        Server serverInstance = (Server) constructor.newInstance(this.commandBus, port);
        
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
