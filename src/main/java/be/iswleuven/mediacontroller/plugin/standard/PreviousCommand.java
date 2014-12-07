package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Player;

import com.google.inject.Inject;

public class PreviousCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "previous";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "\t\tGa naar het vorige liedje in de playlist.";
  
  /**
   * The player instance.
   */
  private final Player player;
  
  /**
   * Create a new previous command.
   * 
   * @param player
   * @param plugin
   */
  @Inject
  public PreviousCommand(Player player, StandardPlugin plugin) {
    super(plugin);
    this.player = player;
  }

  @Override
  public void execute() throws CommandException {
    this.player.previous();
  }

}
