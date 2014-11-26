package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Player;

import com.google.inject.Inject;

public class PositionCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "position";
  
  /**
   * The player instance.
   */
  private final Player player;
  
  /**
   * Create a new position command.
   * 
   * @param player
   * @param plugin
   */
  @Inject
  public PositionCommand(Player player, StandardPlugin plugin) {
    super(plugin);
    this.player = player;
  }

  @Override
  public void execute() throws CommandException {
    try {
      this.player.playAtPosition(Integer.parseInt(getParameters()[0]) - 1);
    } catch (Exception e) {
      throw new CommandException("Positie moet een getal zijn.");
    }
  }

}
