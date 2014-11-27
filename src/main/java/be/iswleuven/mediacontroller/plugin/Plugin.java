package be.iswleuven.mediacontroller.plugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import be.iswleuven.mediacontroller.MediaController;
import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.HelpCommand;

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
  private Map<String, Class<? extends Command>> commands;

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
    this.commands = new HashMap<String, Class<? extends Command>>();
    
    this.commands.put("help", HelpCommand.class);
    
    initializeCommands();
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
  public Map<String, Class<? extends Command>> getCommands() {
    return commands;
  }
  
  /**
   * Set the commands.
   * 
   * @param commands
   */
  public void setCommands(Map<String, Class<? extends Command>> commands) {
    this.commands = commands;
  }
  
  /**
   * Register the command to the plugin.
   * 
   * @param commandClass
   * @return
   * @throws Exception
   */
  public void registerCommand(Class<? extends Command> commandClass) {
    try {
      Field field = commandClass.getDeclaredField("COMMAND_STRING");
      String command = (String) field.get(null);
      
      try {
        field = commandClass.getDeclaredField("COMMAND_ALIASES");
        String[] aliases = (String[]) field.get(null);
        
        for (String alias : aliases) {
          commands.put(alias, commandClass);
        }
      } catch (Exception e) {}
      
      commands.put(command, commandClass);
    } catch (NoSuchFieldException e) {
      if (MediaController.verbose) {
        System.out.println("Het commando " + commandClass.toString() + " kon niet geregistreerd worden.");
        System.out.println("Heeft het commando een publieke statische COMMANDO_STRING variabele?");        
      }
    } catch (Exception e) {
      if (MediaController.verbose) {
        System.out.println("Het commando " + commandClass.toString() + " kon niet geregistreerd worden.");
        e.printStackTrace();
      }
    }    
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
  public abstract void initializeCommands();
  
}
