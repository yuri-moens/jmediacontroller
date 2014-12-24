package be.iswleuven.mediacontroller.plugin.youtube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import be.iswleuven.mediacontroller.cache.Cache;
import be.iswleuven.mediacontroller.cache.Entry;
import be.iswleuven.mediacontroller.config.Config;
import be.iswleuven.mediacontroller.dependency.Dependency;
import be.iswleuven.mediacontroller.dependency.DependencyHandler;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class YoutubeCache extends Cache {
  
  /**
   * The YouTubeDL dependency.
   */
  private final Dependency youtubeDl;

  /**
   * Create a new YouTube cache.
   * 
   * @param config
   * @param dependencyHandler
   */
  @Inject
  public YoutubeCache(Config config, DependencyHandler dependencyHandler) {
    super(config);
    this.youtubeDl = dependencyHandler.getDependency("youtube-dl");
  }
  
  @Override
  public void write(String url, String title, String duration) {
    if (! isCached(url) && ! isTooLong(duration)) {
      if (this.cache.size() >= this.MAX_ENTRIES) {
        forget(this.cache.first());  
      }
      
      Entry entry = new Entry(url, title, downloadSong(url));
      
      this.cache.add(entry);
      this.cacheFile.add(entry);
    }
  }
  
  /**
   * Check if the duration of the song is too long to be cached.
   * 
   * @param duration
   * @return
   */
  private boolean isTooLong(String duration) {
    return duration.matches(".*(([2-9]H)|(\\d\\d+H)).*");
  }
  
  /**
   * Download the song from YouTube and save it.
   * 
   * @return
   */
  private String downloadSong(String url) {
    String path = null;
    
    try {
      Process p = Runtime.getRuntime()
          .exec("python " + youtubeDl.getFile().getAbsolutePath() + " -f bestaudio"
              + " -o " + System.getProperty("user.home") + "/.mediacontroller/cache/%(title)s.%(ext)s"
              + " " + url);

      BufferedReader outputReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
      
      String line;
      while ((line = outputReader.readLine()) != null) {
        if (line.contains("Destination: ")) {
          path = line.replace("[download] Destination: ", "");
        }
      }
      
      outputReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return path;
  }

}
