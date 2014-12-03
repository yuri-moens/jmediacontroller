package be.iswleuven.mediacontroller.dependency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class YoutubeDl extends Dependency {

  /**
   * Create a new youtube-dl dependency.
   * 
   * @param name
   * @param file
   */
  public YoutubeDl(String name, File file) {
    super(name, file);
  }

  @Override
  public void install() throws DependencyException {
    if (isInstalled()) {
      setOutput("youtube-dl is al geïnstalleerd.");
    } else {
      try {
        URL youtubeDlUrl = new URL("https://yt-dl.org/downloads/latest/youtube-dl");
        
        ReadableByteChannel rbc = Channels.newChannel(youtubeDlUrl.openStream());
        
        FileOutputStream fos = new FileOutputStream(getFile());
        
        fos.getChannel().transferFrom(rbc, 0, Integer.MAX_VALUE);
        
        fos.close();
        
        setOutput("youtube-dl werd geïnstalleerd.");
      } catch (Exception e) {
        throw new DependencyException("youtube-dl kon niet gedownload worden.");
      }
    }
  }

  @Override
  public void remove() {
    if (isInstalled()) {
      getFile().delete();
      
      setOutput("youtube-dl werd verwijderd.");
    } else {
      setOutput("youtube-dl is niet geïnstalleerd.");
    }
  }

  @Override
  public void update() {
    if (isInstalled()) {
      try {
        Process p = Runtime.getRuntime().exec("python " + getFile().getAbsolutePath() + " -U");
  
        BufferedReader outputReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        
        setOutput("[youtube-dl] " + outputReader.readLine());
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      setOutput("youtube-dl is niet geïnstalleerd");
    }
  }

  @Override
  public boolean isInstalled() {
    return getFile().exists();
  }
  
  @Override
  public boolean isOutdated() {
    return true;
  }

}
