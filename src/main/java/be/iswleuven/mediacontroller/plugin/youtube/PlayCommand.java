package be.iswleuven.mediacontroller.plugin.youtube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;

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
    String query = StringUtils.join(getParameters(), " ");
    
    try {
      this.playlist.addSong(getSong(query));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Create a song object from the given query.
   * 
   * @param query
   * @return
   * @throws IOException
   */
  private YoutubeSong getSong(String query) throws IOException {    
    Process p = Runtime.getRuntime()
        .exec("python src/main/resources/youtube-dl --skip-download -f bestaudio -e " + query);
    
    BufferedReader outputReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
    
    String title = outputReader.readLine();
    
    return new YoutubeSong(title, query, getWorker().getAddress());
  }

}
