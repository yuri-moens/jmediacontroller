package be.iswleuven.mediacontroller.cache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

public class CacheFile {

  /**
   * The cache file.
   */
  private final File file;
  
  /**
   * Create a new cache file.
   */
  public CacheFile() {
    this.file = new File(System.getProperty("user.home") + "/.mediacontroller/cache/cache-entries");
    File cacheDir = new File (this.file.getParent());
    
    if (! cacheDir.exists()) {
      cacheDir.mkdirs();
    }
    
    try {
      this.file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Parse the given file to get the cached entries.
   * 
   * @param file
   * @return
   * @throws CacheException 
   */
  public SortedSet<Entry> parse() throws CacheException {
    SortedSet<Entry> entries = new TreeSet<Entry>();
    
    try {
      BufferedReader reader = new BufferedReader(new FileReader(this.file));
      String line;
      
      while ((line = reader.readLine()) != null) {
        String[] splittedLine = line.split("\t");
        
        if (splittedLine.length != 4) {
          reader.close();
          throw new CacheException("Error while reading cache file. Malformed entry.");
        }
        
        Date timestamp = new Date(Long.parseLong(splittedLine[3]));
        
        entries.add(new Entry(splittedLine[0], splittedLine[1], splittedLine[2], timestamp));
      }
      
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return entries;
  }
  
  /**
   * Add the given entry to the cache file.
   * 
   * @param entry
   */
  public void add(Entry entry) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(this.file, true));
      
      writer.write(entry.toString());
      writer.newLine();
      
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Remove the given entry from the cache file.
   * 
   * @param entry
   */
  public void remove(Entry entry) {
    try {
      File tmpFile = new File(this.file.getAbsoluteFile() + ".tmp");
      
      BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile));
      BufferedReader reader = new BufferedReader(new FileReader(this.file));
      
      String lineToRemove = entry.toString();
      String line;
      
      while ((line = reader.readLine()) != null) {
        String trimmedLine = line.trim();
        
        if (trimmedLine.equals(lineToRemove)) {
          continue;
        }
        
        writer.write(line);
        writer.newLine();
      }
      
      writer.close();
      reader.close();
      
      tmpFile.renameTo(this.file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}
