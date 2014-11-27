package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Player;

import com.google.inject.Inject;

public class CurrentCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "current";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "\t\tToon het huidig liedje.";
  
  /**
   * The player instance.
   */
  private final Player player;
  
  /**
   * Create a new next command.
   * 
   * @param player
   * @param plugin
   */
  @Inject
  public CurrentCommand(Player player, StandardPlugin plugin) {
    super(plugin);
    this.player = player;
  }

  @Override
  public void execute() throws CommandException {
    setMessage(this.player.getCurrentlyPlaying());
    
    notifyWorker();
  }
  
}
