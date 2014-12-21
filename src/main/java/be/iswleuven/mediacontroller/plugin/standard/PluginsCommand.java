package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.plugin.Plugin;
import be.iswleuven.mediacontroller.plugin.PluginHandler;

import com.google.inject.Inject;

public class PluginsCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "plugins";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "\t\tToon alle beschikbare plugins.";
  
  /**
   * The plugin  handler instance.
   */
  private final PluginHandler pluginHandler;
  
  /**
   * Create a new plugin command.
   * 
   * @param plugin
   */
  @Inject
  public PluginsCommand(PluginHandler pluginHandler, StandardPlugin plugin) {
    super(plugin);
    this.pluginHandler = pluginHandler;
  }

  @Override
  public void execute() throws CommandException {
    String output = "";
    
    for (Plugin plugin : this.pluginHandler.getPlugins().values()) {
      output += plugin.getCommandNamespace() + " - " +  plugin.toString() + "\n";
    }
    
    setMessage(output);
  }
  
}
