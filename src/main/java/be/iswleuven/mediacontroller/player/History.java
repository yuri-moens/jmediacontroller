package be.iswleuven.mediacontroller.player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class History {
  
  /**
   * The maximum history size.
   */
  private final int MAX_HISTORY_SIZE;
  
  /**
   * The list of songs in the history.
   */
  private Deque<Song> history;
  
  @Inject
  public History() {
    this.history = new LinkedList<Song>();

    this.MAX_HISTORY_SIZE = 2;
  }
  
  /**
   * Add the song to the history.
   * 
   * @param song
   */
  public void add(Song song) {
    if (this.history.size() == this.MAX_HISTORY_SIZE) {
      this.history.removeFirst();
    }
    
    writeToFile(song);
    
    this.history.add(song);
  }
  
  /**
   * Write the given song to a history log file.
   * 
   * @param song
   */
  private void writeToFile(Song song) {
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
