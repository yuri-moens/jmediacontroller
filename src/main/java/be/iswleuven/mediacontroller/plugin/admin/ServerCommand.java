package be.iswleuven.mediacontroller.plugin.admin;

import java.util.Map;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.server.Server;
import be.iswleuven.mediacontroller.server.ServerHandler;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ServerCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "server";
  
  /**
   * The injector for this command.
   */
  public static Injector injector = Guice.createInjector(new ServerModule());
  
  /**
   * The server handler instance.
   */
  private ServerHandler serverHandler;
  
  /**
   * Create a new admin server command.
   * 
   * @param serverHandler
   */
  @Inject
  public ServerCommand(ServerHandler serverHandler) {
    this.serverHandler = serverHandler;
  }
  
  @Override
  public void execute() {
    switch (this.parameters[0]) {
      case "list": list();
        break;
      case "start": start(this.parameters[1]);
        break;
      case "stop": stop(this.parameters[1]);
        break;
      case "restart": restart(this.parameters[1]);
        break;
    }
    
    notifyWorker();
  }
  
  private void list() {
    Map<String, Server> servers = this.serverHandler.getServers();
    
    String output = "Geregistreerde servers:";
    
    for (String server : servers.keySet()) {
      output += "\n\t" + server;
    }
    
    setMessage(output);
  }
  
  private void start(String server) {
    if (server.equals("all")) {
      this.serverHandler.startAllServers();
      setMessage("Alle servers werden gestart.");
    } else {
      this.serverHandler.startServer(server);
      setMessage("Server \"" + server + "\" werd gestart.");
    }
  }
  
  private void stop(String server) {
    if (server.equals("all")) {
      this.serverHandler.stopAllServers();
      setMessage("Alle servers werden gestopt.");
    } else {
      this.serverHandler.stopServer(server);
      setMessage("Server \"" + server + "\" werd gestopt.");
    }
  }
  
  private void restart(String server) {
    stop(server);
    start(server);
  }

}
