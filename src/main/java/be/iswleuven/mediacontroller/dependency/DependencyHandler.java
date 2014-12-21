package be.iswleuven.mediacontroller.dependency;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DependencyHandler {

  /**
   * The dependencies directory path.
   */
  private final String dependenciesDirectory;
  
  /**
   * The map with dependencies.
   */
  private final Map<String, Dependency> dependencies;
  
  /**
   * Create a new dependency handler.
   */
  @Inject
  public DependencyHandler() {
    this.dependenciesDirectory = System.getProperty("user.home") + "/.mediacontroller/dependencies/";
    this.dependencies = new HashMap<String, Dependency>();
    
    setupDependencies();
  }
  
  /**
   * Get the dependency with the given name.
   * 
   * @return
   */
  public Dependency getDependency(String dependencyName) {
    return this.dependencies.get(dependencyName);
  }
  
  /**
   * Get all the dependencies.
   * 
   * @return
   */
  public Collection<Dependency> getDependencies() {
    return this.dependencies.values();
  }
  
  /**
   * Setup the dependencies.
   */
  private void setupDependencies() {
    File dependenciesDir = new File(dependenciesDirectory);
    
    if (! dependenciesDir.exists()) {
      dependenciesDir.mkdirs();
    }
    
    this.dependencies.put("youtube-dl", new YoutubeDl("youtube-dl", new File(dependenciesDirectory + "youtube-dl")));
  }
  
}
