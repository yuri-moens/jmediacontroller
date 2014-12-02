package be.iswleuven.mediacontroller.plugin.admin;

import be.iswleuven.mediacontroller.plugin.Plugin;

public class AdminPlugin extends Plugin {

  /**
   * Create a new admin plugin.
   */
  public AdminPlugin() {
    super("AdminPlugin", "1.0", "admin", "Yuri Moens");
  }

  @Override
  public void initializeCommands() {
    registerCommand(ServerCommand.class);
    registerCommand(UpdateCommand.class);
  }
  
}
