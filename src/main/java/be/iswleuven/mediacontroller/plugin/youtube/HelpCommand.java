package be.iswleuven.mediacontroller.plugin.youtube;

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
  public static final String COMMAND_HELP = "\t\tToon dit menu";

  /**
   * Create a new Youtube help command.
   * 
   * @param plugin
   */
  @Inject
  public HelpCommand(YoutubePlugin plugin) {
    super(plugin);
  }

}
