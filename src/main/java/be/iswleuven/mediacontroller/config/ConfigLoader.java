package be.iswleuven.mediacontroller.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import be.iswleuven.mediacontroller.MediaController;

public class ConfigLoader {

  /**
   * The config instance.
   */
  private static Config config;
  
  /**
   * Get the config.
   * 
   * @return
   */
  public static Config getConfig() {
    if (ConfigLoader.config == null) {
      ConfigLoader.load(new File(System.getProperty("user.home") + "/.mediacontroller/mc.conf"));
    }
    
    return ConfigLoader.config;
  }
  
  /**
   * Attempt to load in the given configuration file. If the file is not found it will create a new file with
   * sane defaults.
   * 
   * @param configFile
   * @return
   */
  public static void load(File configFile) {
    Properties properties = new Properties();
    Config config;
    
    try {
      properties.load(new FileInputStream(configFile));
      config = new Config(properties);
    } catch (IOException e) {
      if (MediaController.verbose) {
        System.out.println("Configuratiebestand niet gevonden. Nieuw bestand wordt aangemaakt: "
            + configFile.getAbsolutePath());
      }
      
      config = new Config();
      save(config, configFile);
    }
    
    ConfigLoader.config = config;
  }
  
  /**
   * Write the configuration to disk.
   * 
   * @param config
   * @param configFile
   */
  private static void save(Config config, File configFile) {
    try {
      configFile.getParentFile().mkdirs();
      FileOutputStream out = new FileOutputStream(configFile, false);
      config.getProperties().store(out, "Mediacontroller configuration file");
    } catch (IOException e) {
      if (MediaController.verbose) {
        System.out.println("Kan configuratiebestand niet wegschrijven: " + configFile.getAbsolutePath());
        e.printStackTrace();
      }
      System.exit(1);      
    }
  }
  
}
