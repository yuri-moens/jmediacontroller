package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Player;

import com.google.inject.Inject;

public class VolumeCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "volume";
  
  /**
   * Command aliases.
   */
  public static String[] COMMAND_ALIASES = { "vol" };
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = " [level]\tZet het volumeniveau of geef het huidig niveau weer.";
  
  /**
   * The player instance.
   */
  private final Player player;
  
  /**
   * Create a new volume command.
   * 
   * @param player
   * @param plugin
   */
  @Inject
  public VolumeCommand(Player player, StandardPlugin plugin) {
    super(plugin);
    this.player = player;
  }

  @Override
  public void execute() throws CommandException {
    try {
      this.player.setVolume(Integer.parseInt(getParameters()[0]));
    } catch (NumberFormatException e) {
      throw new CommandException("Volumeniveau moet een getal zijn.");
    } catch (Exception e) {
      setMessage("Volume: " + this.player.getVolume() + "%");
    }
  }
  
}
