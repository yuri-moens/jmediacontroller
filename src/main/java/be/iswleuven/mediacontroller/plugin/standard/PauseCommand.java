package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Player;

import com.google.inject.Inject;

public class PauseCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "pause";
  
  /**
   * The player instance.
   */
  private final Player player;
  
  /**
   * Create a new pause command.
   * 
   * @param player
   * @param plugin
   */
  @Inject
  public PauseCommand(Player player, StandardPlugin plugin) {
    super(plugin);
    this.player = player;
  }

  @Override
  public void execute() throws CommandException {
    this.player.pause();
  }
  
}
