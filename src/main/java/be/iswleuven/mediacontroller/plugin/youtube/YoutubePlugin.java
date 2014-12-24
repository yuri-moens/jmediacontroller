package be.iswleuven.mediacontroller.plugin.youtube;

import be.iswleuven.mediacontroller.plugin.Plugin;

public class YoutubePlugin extends Plugin {
  
  /**
   * Create a new Youtube plugin.
   */
  public YoutubePlugin() {
    super("YouTubePlugin", "1.2.0", "yt", "Yuri Moens");
  }

  @Override
  public void initializeCommands() {
    registerCommand(AddCommand.class);
  }

}
