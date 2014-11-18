package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;

import com.google.inject.Inject;

public class HelpCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "help";
  
  /**
   * Create a new standard help command.
   * 
   * @param plugin
   */
  @Inject
  public HelpCommand(StandardPlugin plugin) {
    super(plugin);
  }
  
  @Override
  public void execute() {
    
  }
  
}
