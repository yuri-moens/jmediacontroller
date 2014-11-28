package be.iswleuven.mediacontroller.plugin.history;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.History;

public class TopCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "top";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = " [month] [amount]";
  
  /**
   * The history.
   */
  private final History history;
  
  /**
   * Create a new top command.
   * 
   * @param history
   * @param plugin
   */
  public TopCommand(History history, HistoryPlugin plugin) {
    super(plugin);
    this.history = history;
  }

  @Override
  public void execute() throws CommandException {
    
  }

}
