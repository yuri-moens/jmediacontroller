package be.iswleuven.mediacontroller.command;

import java.util.Map;

import be.iswleuven.mediacontroller.plugin.Plugin;
import be.iswleuven.mediacontroller.plugin.PluginHandler;
import be.iswleuven.mediacontroller.server.Worker;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class CommandFactory {

  /**
   * The app injector.
   */
  private final Injector injector;
  
  /**
   * The plugin handler instance.
   */
  private final PluginHandler pluginHandler;
  
  /**
   * Create a new command factory.
   * 
   * @param pluginHandler
   */
  @Inject
  public CommandFactory(Injector injector, PluginHandler pluginHandler) {
    this.injector = injector;
    this.pluginHandler = pluginHandler;
  }
  
  /**
   * Create a command from the given raw command string.
   * 
   * @param rawCommand
   * @param worker
   * @return
   * @throws CommandException
   */
  public Command createCommand(String rawCommand, Worker worker) throws CommandException {
    if (rawCommand.startsWith("+") || rawCommand.startsWith("-")) {
      rawCommand = rawCommand.substring(0, 1) + " " + rawCommand.substring(1, rawCommand.length());
    }
    
    String[] rawCommandArray = rawCommand.split(" ");
    
    Plugin plugin = getPlugin(rawCommandArray[0]);
    
    String commandString;
    String[] parameters = null;
    
    Class<? extends Command> commandClass = null;
    
    if (plugin.getName().equals("StandardPlugin")) {
      commandString = rawCommandArray[0];
      commandClass = findCommandClass(plugin, commandString);
      parameters = parseParametersFromRawCommandArray(rawCommandArray, 1);
    } else {
      try {
        commandString = rawCommandArray[1];
        
        if (plugin.getCommands().containsKey(commandString)) {
          parameters = parseParametersFromRawCommandArray(rawCommandArray, 2);        
        } else {
          parameters = parseParametersFromRawCommandArray(rawCommandArray, 1);        
        }
      } catch (ArrayIndexOutOfBoundsException e) {
        commandString = "default";
        
        parameters = new String[0];
      }
    
      commandClass = findCommandClass(plugin, commandString);
    }
    
    Command command = null;
    
    if (commandClass.equals(HelpCommand.class)) {
      command = new HelpCommand(plugin);
    } else {
      command = this.injector.getInstance(commandClass);
      
      command.setParameters(parameters);
    }
    
    command.registerWorker(worker);
    
    return command;
  }
  
  /**
   * Parse the parameters from the raw command.
   * 
   * @param rawCommandArray
   * @param standardPlugin
   * @return
   */
  private String[] parseParametersFromRawCommandArray(String[] rawCommandArray, int offset) {
    String[] parameters = new String[rawCommandArray.length - offset];
    
    for (int i = offset; i < rawCommandArray.length; i++) {
      parameters[i - offset] = rawCommandArray[i];
    }
    
    return parameters;
  }
  
  /**
   * Find the command class for the given raw command. Can throw a CommandNotFoundException when a command
   * could not be found.
   * 
   * @param rawCommand
   * @return
   * @throws CommandNotFoundException
   */
  private Class<? extends Command> findCommandClass(Plugin plugin, String commandString)
      throws CommandNotFoundException {   
    Class<? extends Command> commandClass = plugin.getCommands().get(commandString);
    
    if (commandClass == null) {
      commandClass = plugin.getCommands().get("default");
    }
    
    if (commandClass == null) {
      throw new CommandNotFoundException(commandString);
    }
    
    return commandClass;
  }
  
  /**
   * Get the plugin registered with the given namespace.
   * Returns the standard plugin if a plugin was not found.
   * 
   * @param pluginNamespace
   * @return
   */
  private Plugin getPlugin(String pluginNamespace) {
    Map<String, Plugin> plugins = this.pluginHandler.getPlugins();

    return plugins.get(pluginNamespace) == null ? plugins.get("default") : plugins.get(pluginNamespace);
  }
  
}
