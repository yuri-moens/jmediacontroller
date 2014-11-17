package be.iswleuven.mediacontroller.command;

import java.util.Map;

import be.iswleuven.mediacontroller.plugin.Plugin;
import be.iswleuven.mediacontroller.plugin.PluginHandler;
import be.iswleuven.mediacontroller.util.Observer;

import com.google.inject.Injector;

public class CommandFactory {

  public Command createCommand(String rawCommand, Observer obs) throws CommandException {
    String[] rawCommandArray = rawCommand.split(" ");
    
    Plugin plugin = getPlugin(rawCommandArray[0]);
    
    String commandString;
    String[] parameters;
    
    if (plugin.getName().equals("StandardPlugin")) {
      commandString = rawCommandArray[0];
      parameters = parseParametersFromRawCommandArray(rawCommandArray, true);
    } else {
      commandString = rawCommandArray[1];
      parameters = parseParametersFromRawCommandArray(rawCommandArray, false);
    }

    Class<? extends Command> commandClass = findCommandClass(plugin, commandString);

    Command command = null;
    
    try {
      Injector injector = (Injector) commandClass.getDeclaredField("injector").get(null);
      
      command = injector.getInstance(commandClass);
    } catch (Exception e) {
      try {
        command = commandClass.newInstance();
      } catch (InstantiationException | IllegalAccessException e1) {
        e1.printStackTrace();
      }
    }
    
    command.setParameters(parameters);
    
    command.registerObserver(obs);
    
    return command;
  }
  
  /**
   * Parse the parameters from the raw command.
   * 
   * @param rawCommandArray
   * @param standardPlugin
   * @return
   */
  private String[] parseParametersFromRawCommandArray(String[] rawCommandArray, boolean standardPlugin) {
    int offset = standardPlugin ? 1 : 2;
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
  private Class<? extends Command> findCommandClass(Plugin plugin, String commandString) throws CommandNotFoundException {   
    Class<? extends Command> commandClass = plugin.getCommands().get(commandString);
    
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

    return plugins.get(pluginNamespace) == null ? plugins.get("standard") : plugins.get(pluginNamespace);
  }
  
}
