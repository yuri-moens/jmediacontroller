package be.iswleuven.mediacontroller.plugin.youtube;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.config.Config;
import be.iswleuven.mediacontroller.player.Playlist;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.inject.Inject;

public class AddCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "default";
  
  /**
   * The YouTube API handler.
   */
  private static final YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
    public void initialize(HttpRequest request) throws IOException {}
  }).setApplicationName("youtube-cmdline-search-sample").build();
  
  /**
   * The playlist instance.
   */
  private final Playlist playlist;
  
  /**
   * The config instance.
   */
  private final Config config;
  
  /**
   * Create a new play command.
   * 
   * @param plugin
   */
  @Inject
  public AddCommand(Config config, Playlist playlist, YoutubePlugin plugin) {
    super(plugin);
    this.playlist = playlist;
    this.config = config;
  }

  @Override
  public void execute() throws CommandException {
    String query = StringUtils.join(getParameters(), " ");
    
    String[] youtubeInfo = getYoutubeInfo(query);
    
    this.playlist.addSong(getSong(youtubeInfo[0], youtubeInfo[1]));
  }
  
  /**
   * Create a song object from the given query.
   * 
   * @param title
   * @param query
   * @return
   */
  private YoutubeSong getSong(String title, String query) {  
    return new YoutubeSong(title, query, getWorker().getAddress());
  }

  /**
   * Find the YouTube url from the given query.
   * 
   * @param query
   * @return
   */
  private String[] getYoutubeInfo(String query) {
    String[] url = new String[2];
    
    try {
      YouTube.Search.List search = AddCommand.youtube.search().list("id,snippet");
      
      search.setKey(this.config.getYoutubeApiKey());
      search.setQ(query);
      search.setType("video");
      search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
      search.setMaxResults(1L);
      
      SearchListResponse response = search.execute();
      List<SearchResult> results = response.getItems();
      
      if (results != null) {
        for (SearchResult r : results) {
          url[0] = r.getSnippet().getTitle();
          url[1] = "https://www.youtube.com/watch?v=" + r.getId().getVideoId();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return url;
  }
  
}
