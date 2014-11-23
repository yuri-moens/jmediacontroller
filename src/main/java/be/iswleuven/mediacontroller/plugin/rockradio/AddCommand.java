package be.iswleuven.mediacontroller.plugin.rockradio;

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
  public static final String COMMAND_STRING = "default";
  
  /**
   * The map with station aliases.
   */
  private static final Map<String, String> stationAliases;
  static
  {
    stationAliases = new HashMap<String, String>();
    stationAliases.put("60s", "60srock");
    stationAliases.put("80s", "80srock");
    stationAliases.put("80salt", "80salternative");
    stationAliases.put("90s", "90srock");
    stationAliases.put("90salt", "90salternative");
    stationAliases.put("ballads", "rockballads");
    stationAliases.put("beatles", "beatlestribute");
    stationAliases.put("blues", "bluesrock");
    stationAliases.put("death", "deathmetal");
    stationAliases.put("indie", "indierock");
    stationAliases.put("melodeath", "melodicdeathmetal");
    stationAliases.put("thrash", "thrashmetal");
  }
  
  /**
   * The playlist instance.
   */
  private final Playlist playlist;
  
  /**
   * Create a new play command.
   * 
   * @param plugin
   */
  @Inject
  public AddCommand(Playlist playlist, RockRadioPlugin plugin) {
    super(plugin);
    this.playlist = playlist;
  }

  @Override
  public void execute() throws CommandException {
    String parameter = getParameters()[0];
    
    parameter = AddCommand.stationAliases.get(parameter) == null ?
        parameter : AddCommand.stationAliases.get(parameter);

    String url = null;
    
    try {
      url = getStreamUrl(parameter);
    } catch (Exception e) {
      throw new CommandException("Kan de stream URL niet vinden.");
    }
    
    this.playlist.addSong(new Song("Rock Radio - " + parameter, url, this.worker.getAddress()));
  }
  
  /**
   * Get the stream URL for the given parameter.
   * 
   * @param parameter
   * @return
   * @throws Exception
   */
  private String getStreamUrl(String parameter) throws Exception {
    URL pls = new URL("http://listen.rockradio.com/public3/" + parameter + ".pls");
    BufferedReader in = new BufferedReader(new InputStreamReader(pls.openStream()));
    
    String streamUrl = null;
    String inputLine;
    while ((inputLine = in.readLine()) != null) {
      if (inputLine.contains("File1")) {
        streamUrl = inputLine.replace("File1=", "");
      }
    }
    in.close();
    
    return streamUrl;
  }
  
}
