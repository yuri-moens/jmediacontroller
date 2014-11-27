package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Playlist;

import com.google.inject.Inject;

public class PreviousCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "previous";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "\t\tGa naar het vorige liedje in de playlist.";
  
  /**
   * The playlist instance.
   */
  private final Playlist playlist;
  
  /**
   * Create a new previous command.
   * 
   * @param playlist
   * @param plugin
   */
  @Inject
  public PreviousCommand(Playlist playlist, StandardPlugin plugin) {
    super(plugin);
    this.playlist = playlist;
  }

  @Override
  public void execute() throws CommandException {
    this.playlist.toggleSkipCurrent();
    this.playlist.previousSong();
  }

}
