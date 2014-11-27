package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Playlist;

import com.google.inject.Inject;

public class PositionCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "position";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = " <position>\tGa naar een bepaalde positie in de playlist.";
  
  /**
   * The playlist instance.
   */
  private final Playlist playlist;
  
  /**
   * Create a new position command.
   * 
   * @param playlist
   * @param plugin
   */
  @Inject
  public PositionCommand(Playlist playlist, StandardPlugin plugin) {
    super(plugin);
    this.playlist = playlist;
  }

  @Override
  public void execute() throws CommandException {
    try {
      this.playlist.toggleSkipCurrent();
      this.playlist.setPosition(Integer.parseInt(getParameters()[0]) - 1);
    } catch (Exception e) {
      this.playlist.toggleSkipCurrent();
      throw new CommandException("Positie moet een getal zijn.");
    }
  }

}
