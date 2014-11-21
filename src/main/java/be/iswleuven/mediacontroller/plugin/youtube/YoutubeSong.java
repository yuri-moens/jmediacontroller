package be.iswleuven.mediacontroller.plugin.youtube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

import be.iswleuven.mediacontroller.player.Song;

public class YoutubeSong extends Song {

  public YoutubeSong(String title, String url, InetAddress clientAddress) {
    super(title, url, clientAddress);
  }
  
  @Override
  public String getUrl() {
    return parseUrl(super.getUrl());
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
          .exec("python src/main/resources/youtube-dl --skip-download -f bestaudio -g " + url);

      BufferedReader outputReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
      
      streamUrl = outputReader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return streamUrl;
  }

}
