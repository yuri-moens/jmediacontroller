package be.iswleuven.mediacontroller.plugin.admin;

import java.util.HashMap;
import java.util.Map;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.plugin.Plugin;

public class AdminPlugin extends Plugin {

  /**
   * Create a new admin plugin.
   */
  public AdminPlugin() {
    super("AdminPlugin", "1.0", "admin", "Yuri Moens");
    
    initializeCommands();
  }
  
  @Override
  protected void initializeCommands() {
    Map<String, Command> commands = new HashMap<String, Command>();
    
    Command command = new HelpCommand();
    commands.put(command.getCommandString(), command);
    
    setCommands(commands);
  }
  
}
