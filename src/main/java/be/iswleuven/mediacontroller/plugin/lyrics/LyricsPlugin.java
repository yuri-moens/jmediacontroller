package be.iswleuven.mediacontroller.plugin.lyrics;

import be.iswleuven.mediacontroller.plugin.Plugin;

public class LyricsPlugin extends Plugin {

  /**
   * Create a new lyrics plugin.
   */
  public LyricsPlugin() {
    super("LyricsPlugin", "1.0.1", "lyrics", "Yuri Moens");
  }

  @Override
  public void initializeCommands() {
    registerCommand(LyricsCommand.class);
  }
  
}
