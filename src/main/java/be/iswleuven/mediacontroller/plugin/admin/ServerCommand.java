package be.iswleuven.mediacontroller.plugin.admin;

import java.util.Map;

import be.iswleuven.mediacontroller.admin.AdminHandler;
import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.server.Server;
import be.iswleuven.mediacontroller.server.ServerHandler;

import com.google.inject.Inject;

public class ServerCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "server";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = " list\t\tGeef een lijst van alle beschikbare servers.\n"
      + "\t start|stop|restart <server name|all>\tStart, stop of herstart een/alle server(s).";
  
  /**
   * The admin handler instance.
   */
  private final AdminHandler adminHandler;
  
  /**
   * The server handler instance.
   */
  private final ServerHandler serverHandler;
  
  /**
   * Create a new admin server command.
   * 
   * @param serverHandler
   */
  @Inject
  public ServerCommand(AdminHandler adminHandler, AdminPlugin plugin, ServerHandler serverHandler) {
    super(plugin);
    this.adminHandler = adminHandler;
    this.serverHandler = serverHandler;
  }
  
  @Override
  public void execute() throws CommandException {
    if (!adminHandler.isAdmin(getWorker())) {
      throw new NoAdminException();
    }
    
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
