package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Playlist;
import be.iswleuven.mediacontroller.player.Song;

import com.google.inject.Inject;

public class UndoCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "undo";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "\t\t\tVerwijder het laatst toegevoegde liedje.";
  
  /**
   * The playlist instance.
   */
  private final Playlist playlist;
  
  /**
   * Create a new undo command.
   * 
   * @param playlist
   * @param plugin
   */
  @Inject
  public UndoCommand(Playlist playlist, StandardPlugin plugin) {
    super(plugin);
    this.playlist = playlist;
  }

  @Override
  public void execute() throws CommandException {
    Song song = this.playlist.removeLast();
    
    if (song != null) {
      setMessage(song.getTitle() + " verwijderd");
    } else {
      setMessage("Geen liedje gevonden om te verwijderen.");
    }
  }

}
