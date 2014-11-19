package be.iswleuven.mediacontroller.plugin.youtube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Playlist;
import be.iswleuven.mediacontroller.player.Song;

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
    String[] urls = getParameters();
    
    try {
      Song[] songs = getSongs(urls);
      setMessage(songs[0].toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    notifyWorker();
  }
  
  private Song[] getSongs(String[] urls) throws IOException {
    List<Song> songs = new ArrayList<Song>();
    
    for (String url : urls) {
      Process p = Runtime.getRuntime().exec("python src/main/resources/youtube-dl --skip-download -g -e " + url);
      
      BufferedReader outputReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
      
      String title = outputReader.readLine();
      String streamUrl = outputReader.readLine();
      
      Song song = new Song(title, streamUrl, getWorker().getAddress());
      songs.add(song);
    }
    
    return songs.toArray(new Song[songs.size()]);
  }

}
