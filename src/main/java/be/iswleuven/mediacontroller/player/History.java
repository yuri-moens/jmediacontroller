package be.iswleuven.mediacontroller.player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.inject.Singleton;

@Singleton
public class History {
  
  /**
   * Add the song to the history file.
   * 
   * @param song
   */
  public void add(Song song) {
    String filename = getYearAndMonth() + ".log";
    
    File file = new File(System.getProperty("user.home") + "/.mediacontroller/history/" + filename);
    
    try {
      if (! file.exists()) {
        file.getParentFile().mkdirs();
        file.createNewFile();
      }
      
      PrintWriter writer = new PrintWriter(new FileOutputStream(file, true));
      
      writer.println(song.getTitle() + "," + song.getClientAddress().toString());
      
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Get the current month.
   * 
   * @return
   */
  private String getYearAndMonth() {    
    return new SimpleDateFormat("yyyy-MMMM").format(new Date(System.currentTimeMillis())).toLowerCase();
  }
  
}
