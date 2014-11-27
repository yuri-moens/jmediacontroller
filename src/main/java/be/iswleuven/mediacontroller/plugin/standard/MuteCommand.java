package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Player;

import com.google.inject.Inject;

public class MuteCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "mute";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "\t\t\tMute de speler.";
  
  /**
   * The player instance.
   */
  private final Player player;
  
  /**
   * Create a new mute command.
   * 
   * @param player
   * @param plugin
   */
  @Inject
  public MuteCommand(Player player, StandardPlugin plugin) {
    super(plugin);
    this.player = player;
  }

  @Override
  public void execute() throws CommandException {
    this.player.mute();
  }
  
}
