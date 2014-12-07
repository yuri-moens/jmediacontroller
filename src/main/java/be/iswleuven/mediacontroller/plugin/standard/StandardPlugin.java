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
    registerCommand(DoorCommand.class);
    registerCommand(MuteCommand.class);
    registerCommand(NextCommand.class);
    registerCommand(PauseCommand.class);
    registerCommand(PlaylistCommand.class);
    registerCommand(PluginsCommand.class);
    registerCommand(PositionCommand.class);
    registerCommand(PreviousCommand.class);
    registerCommand(SkipCommand.class);
    registerCommand(StopCommand.class);
    registerCommand(UndoCommand.class);
    registerCommand(VolumeCommand.class);
    registerCommand(VolumeDownCommand.class);
    registerCommand(VolumeUpCommand.class);
  }
  
}
