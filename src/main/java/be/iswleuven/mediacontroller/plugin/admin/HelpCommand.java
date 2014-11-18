package be.iswleuven.mediacontroller.plugin.admin;

import be.iswleuven.mediacontroller.command.Command;

import com.google.inject.Inject;

public class HelpCommand extends Command {
  
  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "help";

  /**
   * Create a new admin help command.
   * 
   * @param plugin
   */
  @Inject
  public HelpCommand(AdminPlugin plugin) {
    super(plugin);
  }
  
  @Override
  public void execute() {
    String output = "";
    
    setMessage(output);
    notifyWorker();
  }

}
