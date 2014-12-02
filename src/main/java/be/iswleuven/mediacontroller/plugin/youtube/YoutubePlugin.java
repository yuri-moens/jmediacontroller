package be.iswleuven.mediacontroller.plugin.youtube;

import be.iswleuven.mediacontroller.plugin.Plugin;

public class YoutubePlugin extends Plugin {
  
  /**
   * The location of youtube-dl.
   */
  public static final String YOUTUBE_DL = System.getProperty("user.home") + "/.mediacontroller/dependencies/youtube-dl";
  
  /**
   * Create a new Youtube plugin.
   */
  public YoutubePlugin() {
    super("YouTubePlugin", "1.0", "yt", "Yuri Moens");
  }

  @Override
  public void initializeCommands() {
    registerCommand(AddCommand.class);
  }

}
