package be.iswleuven.mediacontroller.plugin;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import be.iswleuven.mediacontroller.config.Config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.metapossum.utils.scanner.ResourceNameFilter;
import com.metapossum.utils.scanner.reflect.ClassesInPackageScanner;

@Singleton
public class PluginHandler {
  
  /**
   * Map with the plugins and their command namespace.
   */
  private Map<String, Plugin> plugins;
  
  /**
   * Create a new plugin handler.
   */
  @Inject
  public PluginHandler(Config config) {
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
              return fileName.endsWith(plugin + ".class");
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
