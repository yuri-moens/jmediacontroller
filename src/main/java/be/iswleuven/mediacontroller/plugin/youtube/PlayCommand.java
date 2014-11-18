package be.iswleuven.mediacontroller.plugin.youtube;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Playlist;

import com.google.inject.Inject;

public class PlayCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "default";
  
  /**
   * The playlist instance.
   */
  private Playlist playlist;
  
  /**
   * Create a new play command.
   * 
   * @param plugin
   */
  @Inject
  public PlayCommand(YoutubePlugin plugin, Playlist playlist) {
    super(plugin);
    this.playlist = playlist;
  }

  @Override
  public void execute() throws CommandException {
    this.playlist.addSong(null);
  }

}
