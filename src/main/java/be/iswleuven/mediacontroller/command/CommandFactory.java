package be.iswleuven.mediacontroller.command;

import java.util.Map;

import be.iswleuven.mediacontroller.plugin.Plugin;
import be.iswleuven.mediacontroller.plugin.PluginHandler;
import be.iswleuven.mediacontroller.server.Worker;

import com.google.inject.Injector;

public class CommandFactory {

  /**
   * Create a command from the given raw command string.
   * 
   * @param rawCommand
   * @param worker
   * @return
   * @throws CommandException
   */
  public Command createCommand(String rawCommand, Worker worker) throws CommandException {
    String[] rawCommandArray = rawCommand.split(" ");
    
    Plugin plugin = getPlugin(rawCommandArray[0]);
    
    String commandString;
    String[] parameters;
    
    Class<? extends Command> commandClass;
    
    if (plugin.getName().equals("StandardPlugin")) {
      commandString = rawCommandArray[0];
      commandClass = findCommandClass(plugin, commandString);
      parameters = parseParametersFromRawCommandArray(rawCommandArray, 1);
    } else {
      try {
        commandString = rawCommandArray[1];
      
        commandClass = findCommandClass(plugin, commandString);
        
        if (plugin.getCommands().containsKey(commandString)) {
          parameters = parseParametersFromRawCommandArray(rawCommandArray, 2);        
        } else {
          parameters = parseParametersFromRawCommandArray(rawCommandArray, 1);        
        }
      } catch (ArrayIndexOutOfBoundsException e) {
        throw new NoParameterException();
      }
    }

    Injector injector = null;
    
    try {
      injector = (Injector) commandClass.getDeclaredField("injector").get(null);
    } catch (Exception e) {
      try {
        injector = (Injector) Command.class.getDeclaredField("injector").get(null);
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
    }
    
    Command command = injector.getInstance(commandClass);
    
    command.setParameters(parameters);
    
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
    Map<String, Plugin> plugins = PluginHandler.getInstance().getPlugins();

    return plugins.get(pluginNamespace) == null ? plugins.get("default") : plugins.get(pluginNamespace);
  }
  
}
