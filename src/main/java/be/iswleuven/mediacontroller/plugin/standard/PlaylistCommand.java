package be.iswleuven.mediacontroller.plugin.standard;

import java.util.List;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Playlist;
import be.iswleuven.mediacontroller.player.Song;

import com.google.inject.Inject;

public class PlaylistCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "playlist";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "\t\tToon de huidige playlist.";
  
  /**
   * The playlist instance.
   */
  private final Playlist playlist;
  
  /**
   * Create a new playlist command.
   * 
   * @param playlist
   * @param plugin
   */
  @Inject
  public PlaylistCommand(Playlist playlist, StandardPlugin plugin) {
    super(plugin);
    this.playlist = playlist;
  }

  @Override
  public void execute() throws CommandException {
    List<Song> songs = this.playlist.getSongs();
    
    String output = "";
    int i = 1;
    
    for (Song song : songs) {
      if (this.playlist.getPosition() == i - 1) {
        output += i++ + ". " + song.toString() + " [ HUIDIG ]\n";
      } else {
        output += i++ + ". " + song.toString() + "\n";
      }
    }
    
    setMessage(output);
    
    notifyWorker();
  }

}
