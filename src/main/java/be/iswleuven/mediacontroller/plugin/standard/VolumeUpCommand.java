package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Player;

import com.google.inject.Inject;

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
   * The command help string.
   */
  public static final String COMMAND_HELP = "\t\t\tZet het volume 5% hoger.";
  
  /**
   * Create a new volume up command.
   * 
   * @param player
   * @param plugin
   */
  @Inject
  public VolumeUpCommand(Player player, StandardPlugin plugin) {
    super(plugin);
    this.player = player;
  }

  @Override
  public void execute() throws CommandException {
    int symbolAmount = getParameters().length > 0 ? getParameters()[0].length() + 1 : 1;
    
    int volume = 5 * symbolAmount;
    int currentVolume = this.player.getVolume() + volume > 100 ? 100 : this.player.getVolume() + volume;
    
    setMessage("Volume: " + currentVolume + "%");
    
    this.player.changeVolume(volume);
  }

}
