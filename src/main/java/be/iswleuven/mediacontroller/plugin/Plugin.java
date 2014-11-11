package be.iswleuven.mediacontroller.plugin;

import java.util.Map;

import be.iswleuven.mediacontroller.command.Command;

public abstract class Plugin {
  
  /**
   * The name of the plugin.
   */
  private final String name;
  
  /**
   * The version of the plugin.
   */
  private final String version;
  
  /**
   * The array of authors of the plugin.
   */
  private final String[] authors;
  
  /**
   * The namespace of all the commands associated with the plugin.
   */
  private final String commandNamespace;
  
  /**
   * The commands this plugin provides.
   */
  private Map<String, Command> commands;

  /**
   * Create a new plugin.
   * 
   * @param name
   * @param version
   * @param commandNamespace
   * @param authors
   */
  public Plugin(String name, String version, String commandNamespace, String... authors) {
    this.name = name;
    this.version = version;
    this.commandNamespace = commandNamespace;
    this.authors = authors;
  }
  
  /**
   * Get the name.
   * 
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * Get the authors.
   * 
   * @return
   */
  public String[] getAuthors() {
    return authors;
  }

  /**
   * Get the version.
   * 
   * @return
   */
  public String getVersion() {
    return version;
  }
  
  /**
   * Get the command namespace.
   * 
   * @return
   */
  public String getCommandNamespace() {
    return commandNamespace;
  }

  /**
   * Get the commands.
   * 
   * @return
   */
  public Map<String, Command> getCommands() {
    return commands;
  }
  
  /**
   * Set the commands.
   * 
   * @param commands
   */
  public void setCommands(Map<String, Command> commands) {
    this.commands = commands;
  }
  
  @Override
  public String toString() {
    String output = name + " versie " + version + " door ";
    
    for (String author : authors) {
      output += author + ", ";
    }
    
    return output.replaceFirst(", $", "");
  }
  
  /**
   * Initialize the commands associated with this plugin.
   */
  protected abstract void initializeCommands();
  
}
