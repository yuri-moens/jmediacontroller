package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.plugin.Plugin;

public class StandardPlugin extends Plugin {

  /**
   * Create a new standard plugin.
   */
  public StandardPlugin() {
    super("StandardPlugin", "1.0", "default", "Yuri Moens");
    
    initializeCommands();
  }

  @Override
  public void initializeCommands() {
    registerCommand(HelpCommand.class);
    registerCommand(NextCommand.class);
    registerCommand(PauseCommand.class);
    registerCommand(StopCommand.class);
    registerCommand(VolumeCommand.class);
  }
  
}
