package be.iswleuven.mediacontroller.player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.inject.Singleton;

@Singleton
public class History {
  
  /**
   * Add the song to the history file.
   * 
   * @param song
   */
  public void add(Song song) {
    String filename = getYear() + "-" + getMonth() + ".log";
    
    File file = new File(System.getProperty("user.home") + "/.mediacontroller/history/" + filename);
    
    try {
      if (! file.exists()) {
        file.getParentFile().mkdirs();
        file.createNewFile();
      }
      
      PrintWriter writer = new PrintWriter(new FileOutputStream(file, true));
      
      writer.println(song.getTitle() + " (" + song.getClientAddress().toString() + ")");
      
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
  private String getMonth() {
    return new SimpleDateFormat("MM-MMMM").format(new Date(System.currentTimeMillis())).toLowerCase();
  }
  
  /**
   * Get the current year.
   * 
   * @return
   */
  private String getYear() {
    return new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis())).toLowerCase();    
  }
  
  /**
   * Get the history of all months.
   * 
   * @return
   * @throws FileNotFoundException
   */
  public List<String> getAllHistory() throws FileNotFoundException {
    List<String> history = new ArrayList<String>();
    
    File dir = new File(System.getProperty("user.home") + "/.mediacontroller/history/");
    
    for (File file : dir.listFiles()) {
      history.addAll(readFromFile(file));
    }
    
    return history;
  }
  
  /**
   * Get the history for the current month.
   * 
   * @return
   * @throws FileNotFoundException
   */
  public List<String> getHistory() throws FileNotFoundException {    
    return getHistory(getMonth());
  }
  
  /**
   * Get the history for the given month in the current year.
   * 
   * @param month
   * @return
   * @throws FileNotFoundException
   */
  public List<String> getHistory(String month) throws FileNotFoundException {    
    return getHistory(month, getYear());
  }
  
  /**
   * Get the history for the given month in the given year.
   * 
   * @param month
   * @param year
   * @return
   * @throws FileNotFoundException
   */
  public List<String> getHistory(String month, String year) throws FileNotFoundException {
    File file = new File(System.getProperty("user.home") + "/.mediacontroller/history/" + year + "-" + month + ".log");
    
    return readFromFile(file);
  }
  
  /**
   * Read from the given file and return the lines in a list.
   * 
   * @param file
   * @return
   * @throws FileNotFoundException
   */
  private List<String> readFromFile(File file) throws FileNotFoundException {
    List<String> lines = new ArrayList<String>();
    
    if (! file.exists()) {
      throw new FileNotFoundException();
    }
    
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line;
    
    try {
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
      
      reader.close();
    } catch (Exception e) {}
    
    return lines; 
  }
  
}
