package be.iswleuven.mediacontroller.plugin.admin;

import be.iswleuven.mediacontroller.plugin.Plugin;

public class AdminPlugin extends Plugin {
  
  /**
   * Create a new admin plugin.
   */
  public AdminPlugin() {
    super("AdminPlugin", "1.2.1", "admin", "Yuri Moens");
  }

  @Override
  public void initializeCommands() {
    registerCommand(LoginCommand.class);
    registerCommand(LogoutCommand.class);
    registerCommand(ServerCommand.class);
    registerCommand(UpdateCommand.class);
  }
  
}
