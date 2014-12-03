package be.iswleuven.mediacontroller.plugin.radio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Playlist;
import be.iswleuven.mediacontroller.player.Song;

import com.google.inject.Inject;

public class AddCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "default";
  
  /**
   * The command help string.
   */
  public static String COMMAND_HELP = "<url naar .pls>";
  
  /**
   * The playlist instance.
   */
  private final Playlist playlist;
  
  /**
   * Create a new add command.
   * 
   * @param playlist
   * @param plugin
   */
  @Inject
  public AddCommand(Playlist playlist, RadioPlugin plugin) {
    super(plugin);
    this.playlist = playlist;
  }

  @Override
  public void execute() throws CommandException {
    try {
      Map<String, String> streamInfo = getStreamInfo(getParameters()[0]);
      
      this.playlist.addSong(new Song(streamInfo.get("title"), streamInfo.get("url"), this.worker.getAddress()));
    } catch (Exception e) {
      throw new CommandException("Kan de stream URL niet vinden.");
    }
  }
  
  /**
   * Get the stream info from the pls file.
   */
  private Map<String, String> getStreamInfo(String plsUrl) throws Exception {
    URL pls = new URL(plsUrl);
    BufferedReader in = new BufferedReader(new InputStreamReader(pls.openStream()));
    
    Map<String, String> streamInfo = new HashMap<String, String>(2);
    String inputLine;
    while ((inputLine = in.readLine()) != null) {
      if (inputLine.contains("File1")) {
        streamInfo.put("url", inputLine.replace("File1=", ""));
      } else if (inputLine.contains("Title1")) {
        streamInfo.put("title", inputLine.replace("Title1=", ""));
      }
    }
    in.close();
    
    return streamInfo;
  }

}
