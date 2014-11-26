package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Playlist;

import com.google.inject.Inject;

public class NextCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "next";
  
  /**
   * The playlist instance.
   */
  private final Playlist playlist;
  
  /**
   * Create a new next command.
   * 
   * @param playlist
   * @param plugin
   */
  @Inject
  public NextCommand(Playlist playlist, StandardPlugin plugin) {
    super(plugin);
    this.playlist = playlist;
  }

  @Override
  public void execute() throws CommandException {
    this.playlist.toggleSkipCurrent();
    this.playlist.nextSong();
  }

}
