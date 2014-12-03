package be.iswleuven.mediacontroller.config;

import java.util.Properties;

public class Config {

  /**
   * The configuration properties.
   */
  private Properties properties;
  
  /**
   * Create a config object with default properties.
   */
  public Config() {
    this.properties = getDefaultProperties();
  }
  
  /**
   * Create a config object.
   * 
   * @param properties
   */
  public Config(Properties properties) {
    this.properties = properties;
  }
  
  /**
   * Get the properties.
   * 
   * @return
   */
  public Properties getProperties() {
    return properties;
  }
  
  /**
   * Get the servers that should be loaded.
   * 
   * @return
   */
  public String[] getServers() {
    return properties.getProperty("servers").split(",");
  }
  
  /**
   * Get the plugins that should be loaded.
   * 
   * @return
   */
  public String[] getPlugins() {
    return properties.getProperty("plugins").split(",");
  }
  
  /**
   * Get the maximum history size.
   * 
   * @return
   */
  public int getMaxHistorySize() {
    return Integer.parseInt(properties.getProperty("maxHistorySize"));
  }
  
  /**
   * Get the location of the VLC library.
   * 
   * @return
   */
  public String getVlcLocation() {
    return properties.getProperty("vlcLocation");
  }

  /**
   * Get the Youtube API key.
   * @return
   */
  public String getYoutubeApiKey() {
    return properties.getProperty("YoutubeApiKey");
  }
  
  /**
   * Get default properties.
   * 
   * @return
   */
  private static Properties getDefaultProperties() {
    Properties properties = new Properties();
    
    properties.setProperty("servers", "SocketServer:3333");
    properties.setProperty("plugins", "StandardPlugin,AdminPlugin,HistoryPlugin,YoutubePlugin,RadioPlugin,RockRadioPlugin,RadioTunesPlugin");
    properties.setProperty("player", "VlcPlayer");
    properties.setProperty("maxHistorySize", "100");
    properties.setProperty("YoutubeApiKey", "");
    properties.setProperty("vlcLocation", "");
    
    return properties;
  }
  
}
