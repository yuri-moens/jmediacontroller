package be.iswleuven.mediacontroller.plugin.admin;

import be.iswleuven.mediacontroller.command.AbstractHelpCommand;

import com.google.inject.Inject;

public class HelpCommand extends AbstractHelpCommand {
  
  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "help";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "\t Toon dit menu";

  /**
   * Create a new admin help command.
   * 
   * @param plugin
   */
  @Inject
  public HelpCommand(AdminPlugin plugin) {
    super(plugin);
  }

}
