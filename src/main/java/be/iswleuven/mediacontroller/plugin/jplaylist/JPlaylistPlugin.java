package be.iswleuven.mediacontroller.plugin.jplaylist;

import be.iswleuven.mediacontroller.plugin.Plugin;

public class JPlaylistPlugin extends Plugin {

  /**
   * Create a new JPlaylist plugin.
   */
  public JPlaylistPlugin() {
    super("JPlaylistPlugin", "1.0.0", "jpl", "Yuri Moens");
  }

  @Override
  public void initializeCommands() {
    registerCommand(LoadCommand.class);
  }

}
