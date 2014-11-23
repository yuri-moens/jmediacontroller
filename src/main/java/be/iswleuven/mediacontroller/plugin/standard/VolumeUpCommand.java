package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Player;

public class VolumeUpCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "+";
  
  /**
   * The player instance.
   */
  private final Player player;
  
  /**
   * Create a new volume up command.
   * 
   * @param player
   * @param plugin
   */
  public VolumeUpCommand(Player player, StandardPlugin plugin) {
    super(plugin);
    this.player = player;
  }

  @Override
  public void execute() throws CommandException {
    int symbolAmount = getParameters().length > 0 ? getParameters()[0].length() + 1 : 1;
    
    this.player.changeVolume(5 * symbolAmount);
  }

}
