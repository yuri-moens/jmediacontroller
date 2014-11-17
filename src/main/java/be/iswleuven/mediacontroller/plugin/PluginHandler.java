package be.iswleuven.mediacontroller.plugin;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import be.iswleuven.mediacontroller.MediaController;

import com.metapossum.utils.scanner.ResourceNameFilter;
import com.metapossum.utils.scanner.reflect.ClassesInPackageScanner;

public class PluginHandler {

  /**
   * The plugin handler instance.
   */
  private static PluginHandler pluginHandler;
  
  /**
   * Map with the plugins and their command namespace.
   */
  private Map<String, Plugin> plugins;
  
  /**
   * Create a new plugin handler.
   */
  private PluginHandler() {
    plugins = new HashMap<String, Plugin>();
    addPlugins(MediaController.config.getPlugins());
  }
  
  /**
   * Get the instance of the plugin handler. Create it first if it doesn't exist yet.
   * 
   * @return
   */
  public static PluginHandler getInstance() {
    if (pluginHandler == null) {
      pluginHandler = new PluginHandler();
    }
    
    return pluginHandler;
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
              return fileName.equals(plugin + ".class");
          }
        }).scan("be.iswleuven.mediacontroller.plugin");
        
        if (pluginClasses.size() == 1) {
          Iterator<Class<?>> iterator = pluginClasses.iterator();
          Class<?> pluginClass = iterator.next();
          
          Constructor<?> constructor = pluginClass.getConstructor();
          
          Plugin pluginInstance = (Plugin) constructor.newInstance();
          
          this.plugins.put(pluginInstance.getCommandNamespace(), pluginInstance);
        } else {
          throw new PluginNotFoundException(plugin);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
