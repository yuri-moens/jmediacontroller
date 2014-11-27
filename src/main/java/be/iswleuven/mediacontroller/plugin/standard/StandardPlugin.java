package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.plugin.Plugin;

public class StandardPlugin extends Plugin {

  /**
   * Create a new standard plugin.
   */
  public StandardPlugin() {
    super("StandardPlugin", "1.0", "default", "Yuri Moens");
  }

  @Override
  public void initializeCommands() {
    registerCommand(CurrentCommand.class);
    registerCommand(HelpCommand.class);
    registerCommand(MuteCommand.class);
    registerCommand(NextCommand.class);
    registerCommand(PauseCommand.class);
    registerCommand(PositionCommand.class);
    registerCommand(PreviousCommand.class);
    registerCommand(SkipCommand.class);
    registerCommand(StopCommand.class);
    registerCommand(VolumeCommand.class);
    registerCommand(VolumeDownCommand.class);
    registerCommand(VolumeUpCommand.class);
  }
  
}
