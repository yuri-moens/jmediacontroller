package be.iswleuven.mediacontroller.plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import be.iswleuven.mediacontroller.config.Config;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.metapossum.utils.scanner.ResourceNameFilter;
import com.metapossum.utils.scanner.reflect.ClassesInPackageScanner;

@Singleton
public class PluginHandler {

  /**
   * The logger instance.
   */
  private final static Logger logger = Logger.getLogger(PluginHandler.class);
  
  /**
   * Map with the plugins and their command namespace.
   */
  private Map<String, Plugin> plugins;
  
  /**
   * The app injector.
   */
  private final Injector injector;
  
  /**
   * Create a new plugin handler.
   */
  @Inject
  public PluginHandler(Config config, Injector injector) {
    this.injector = injector;
    
    plugins = new HashMap<String, Plugin>();
    addPlugins(config.getPlugins());
  }
  
  /**
   * Get the plugins map.
   * 
   * @return
   */
  public Map<String, Plugin> getPlugins() {
    return this.plugins;
  }
  
  /**
   * Add the plugins to the plugins map.
   * 
   * @param plugins
   */
  public void addPlugins(String[] plugins) {
    for (final String plugin : plugins) {
      try {
        Set<Class<?>> pluginClasses = new ClassesInPackageScanner().setResourceNameFilter(new ResourceNameFilter() {
          public boolean acceptResourceName(java.lang.String packageName, java.lang.String fileName) {
            fileName = fileName.replaceAll(".*/", "");
            return fileName.equals(plugin + ".class");
          }
        }).scan("be.iswleuven.mediacontroller.plugin");
        
        if (pluginClasses.size() == 1) {
          Iterator<Class<?>> iterator = pluginClasses.iterator();
          Class<?> pluginClass = iterator.next();
          
          Plugin pluginInstance = (Plugin) this.injector.getInstance(pluginClass);
          
          this.plugins.put(pluginInstance.getCommandNamespace(), pluginInstance);
          
          logger.log(Level.INFO, "Added plugin \"" + pluginInstance.getName() + "\"");
        } else {
          throw new PluginNotFoundException(plugin);
        }
      } catch (Exception e) {
        logger.log(Level.ERROR, "Failed to add plugin \"" + plugin + "\"");
      }
    }
  }

}
