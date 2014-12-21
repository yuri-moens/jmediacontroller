package be.iswleuven.mediacontroller.dependency;

import java.io.File;

public abstract class Dependency {

  /**
   * The name of the dependency.
   */
  private final String name;
  
  /**
   * The file of the dependency.
   */
  private final File file;
  
  /**
   * The output of the dependency.
   */
  private String output;
  
  /**
   * Create a new dependency with the given name.
   * 
   * @param name
   */
  public Dependency(String name, File file) {
    this.name = name;
    this.file = file;
  }
  
  /**
   * Get the name of the dependency.
   * 
   * @return
   */
  public String getName() {
    return this.name;
  }
  
  /**
   * Get the file of the dependency.
   * 
   * @return
   */
  public File getFile() {
    return this.file;
  }
  
  /**
   * Get the output of this dependency.
   * 
   * @return
   */
  public String getOutput() {
    return this.output;
  }
  
  /**
   * Set the output of this dependency.
   * 
   * @param output
   */
  public void setOutput(String output) {
    this.output = output;
  }
  
  /**
   * Install the dependency.
   */
  public abstract void install() throws DependencyException;
  
  /**
   * Remove the dependency.
   */
  public abstract void remove() throws DependencyException;
  
  /**
   * Update the dependency.
   */
  public abstract void update() throws DependencyException;
  
  /**
   * Check if the dependency is installed.
   * 
   * @return
   */
  public abstract boolean isInstalled();
  
  /**
   * Check if the dependency is outdated.
   * 
   * @return
   */
  public abstract boolean isOutdated();
  
}
