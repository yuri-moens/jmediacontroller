package be.iswleuven.mediacontroller.plugin.youtube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import be.iswleuven.mediacontroller.cache.Cache;
import be.iswleuven.mediacontroller.cache.Entry;
import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.config.Config;
import be.iswleuven.mediacontroller.dependency.Dependency;
import be.iswleuven.mediacontroller.dependency.DependencyHandler;
import be.iswleuven.mediacontroller.player.Playlist;
import be.iswleuven.mediacontroller.player.Song;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.inject.Inject;

public class AddCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "default";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "<title|url>\tGeef de titel of de URL van de video of playlist.";
  
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
   * The youtube-dl instance.
   */
  private final Dependency youtubeDl;
  
  /**
   * The song cache.
   */
  private final Cache cache;
  
  /**
   * Create a new play command.
   * 
   * @param plugin
   */
  @Inject
  public AddCommand(YoutubeCache cache, Config config, DependencyHandler dependencyHandler, Playlist playlist, YoutubePlugin plugin) {
    super(plugin);
    this.cache = cache;
    this.playlist = playlist;
    this.config = config;
    this.youtubeDl = dependencyHandler.getDependency("youtube-dl");
  }

  @Override
  public void execute() throws CommandException {
    String query = StringUtils.join(getParameters(), " ");
    
    try {
      if (query.contains("list=")) {
        List<String[]> videoInfos = parsePlaylist(query);
        
        int notFound = 0;
        
        for (String[] videoInfo : videoInfos) {
          try {
            this.playlist.addSong(new Song(videoInfo[0], parseUrl(videoInfo[1]), getWorker().getAddress()));
          } catch (IllegalArgumentException e) {
            notFound++;
          }
        }
        
        setMessage(videoInfos.size() + " liedjes toegevoegd aan de playlist.");
        
        if (notFound > 0) {
          setMessage(getMessage() + "\n" + notFound + " liedjes werden niet gevonden.");
        }
      } else {
        final String[] youtubeInfo = getYoutubeInfo(query);
        
        if (cache.isCached(youtubeInfo[1])) {
          Entry entry = cache.read(youtubeInfo[1]);
          this.playlist.addSong(new Song(entry.getTitle(), entry.getPath(), getWorker().getAddress()));
        } else {
          this.playlist.addSong(new Song(youtubeInfo[0], parseUrl(youtubeInfo[1]), getWorker().getAddress()));
          
          new Thread(new Runnable() {

            @Override
            public void run() {
              cache.write(youtubeInfo[1], youtubeInfo[0], youtubeInfo[2]);
            }
            
          }).start();
        }
        
        setMessage(youtubeInfo[0] + " toegevoegd aan de playlist.");
      }
    } catch (IllegalArgumentException e) {
      throw new CommandException("Liedje werd niet gevonden.");
    }
  }

  /**
   * Find the YouTube url from the given query.
   * 
   * @param query
   * @return
   */
  private String[] getYoutubeInfo(String query) {
    String[] youtubeInfo = new String[3];
    
    try {
      YouTube.Search.List search = AddCommand.youtube.search().list("id");
      
      search.setKey(this.config.getYoutubeApiKey());
      search.setQ(query);
      search.setType("video");
      search.setFields("items(id/videoId)");
      search.setMaxResults(1L);
      
      SearchListResponse response = search.execute();
      List<SearchResult> results = response.getItems();
      String videoId = null;
      
      if (results != null && results.size() == 1) {
        videoId = results.get(0).getId().getVideoId();
      }
      
      YouTube.Videos.List video = AddCommand.youtube.videos().list("id,snippet,contentDetails");
      
      video.setKey(this.config.getYoutubeApiKey());
      video.setId(videoId);
      video.setMaxResults(1L);
      
      VideoListResponse videoResponse = video.execute();
      List<Video> videoResults = videoResponse.getItems();
      
      if (videoResults != null && videoResults.size() == 1) {
        Video v = videoResults.get(0);
        
        youtubeInfo[0] = v.getSnippet().getTitle();
        youtubeInfo[1] = "https://www.youtube.com/watch?v=" + v.getId();
        youtubeInfo[2] = v.getContentDetails().getDuration();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return youtubeInfo;
  }
  
  /**
   * Get the video information for the videos in the given YouTube playlist url.
   * 
   * @param playlistUrl
   * @return
   */
  private List<String[]> parsePlaylist(String playlistUrl) {
    String playlistId = playlistUrl.replaceAll(".*list=", "");
    playlistId = playlistId.replaceAll("&.*", "");
    List<String[]> videoInfos = new ArrayList<String[]>();
    
    try {
      YouTube.PlaylistItems.List search = AddCommand.youtube.playlistItems().list("id,snippet");
      
      search.setKey(this.config.getYoutubeApiKey());
      search.setPlaylistId(playlistId);
      search.setMaxResults(50L);
      
      List<PlaylistItem> results = new ArrayList<PlaylistItem>();
      String nextToken = "";
      
      do {
        search.setPageToken(nextToken);
        
        PlaylistItemListResponse response = search.execute();
        results.addAll(response.getItems());
        
        nextToken = response.getNextPageToken();
      } while (nextToken != null);
      
      if (results != null) {
        for (PlaylistItem item : results) {
          String[] videoInfo = new String[2];
          
          videoInfo[0] = item.getSnippet().getTitle();
          videoInfo[1] = "https://www.youtube.com/watch?v=" + item.getSnippet().getResourceId().getVideoId();
          
          videoInfos.add(videoInfo);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return videoInfos;
  }
  
  /**
   * Parse the Youtube URL to get stream link.
   * 
   * @return
   */
  private String parseUrl(String url) {
    String streamUrl = null;
    
    try {
      Process p = Runtime.getRuntime()
          .exec("python " + youtubeDl.getFile().getAbsolutePath() + " --skip-download -f bestaudio -g " + url);

      BufferedReader outputReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
      
      streamUrl = outputReader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return streamUrl;
  }
  
}
