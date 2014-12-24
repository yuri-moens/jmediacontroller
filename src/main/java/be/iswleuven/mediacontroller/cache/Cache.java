package be.iswleuven.mediacontroller.cache;

import java.util.SortedSet;

import be.iswleuven.mediacontroller.config.Config;

public abstract class Cache {

  /**
   * The maximum amount of entries that can be cached.
   */
  protected final int MAX_ENTRIES;
  
  /**
   * The file with the cache entries in.
   */
  protected final CacheFile cacheFile;
  
  /**
   * The set with the cache entries in.
   */
  protected SortedSet<Entry> cache;
  
  /**
   * Create a new cache.
   * 
   * @param config
   */
  public Cache(Config config) {
    this.MAX_ENTRIES = config.getMaxCacheSize();
    this.cacheFile = new CacheFile();
    
    try {
      this.cache = cacheFile.parse();
    } catch (CacheException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Read the entry with the given url from the cache.
   * 
   * @param url
   * @return
   */
  public Entry read(String url) {
    for (Entry e : this.cache) {
      if (e.getUrl().equals(url)) {
        return e;
      }
    }
    
    return null;
  }
  
  /**
   * Remove the given entry from the cache.
   * 
   * @param entry
   */
  public void forget(Entry entry) {
    if (this.cache.contains(entry)) {
      entry.deleteFile();
      this.cache.remove(entry);
      this.cacheFile.remove(entry);
    }
  }
  
  /**
   * Check if the song with the given url is cached.
   * 
   * @param url
   * @return
   */
  public boolean isCached(String url) {
    for (Entry e : this.cache) {
      if (e.getUrl().equals(url)) {
        return true;
      }
    }
    
    return false;
  }
  
  /**
   * Write the song with the given url, title and duration to the cache.
   * 
   * @param url
   * @param title
   * @param duration
   * @return
   */
  public abstract void write(String url, String title, String duration);
  
}
