package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Player;

import com.google.inject.Inject;

public class VolumeDownCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "-";
  
  /**
   * The player instance.
   */
  private final Player player;
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "\t\t\tZet het volume 5% lager.";
  
  /**
   * Create a new volume down command.
   * 
   * @param player
   * @param plugin
   */
  @Inject
  public VolumeDownCommand(Player player, StandardPlugin plugin) {
    super(plugin);
    this.player = player;
  }

  @Override
  public void execute() throws CommandException {
    int symbolAmount = getParameters().length > 0 ? getParameters()[0].length() + 1 : 1;
    
    this.player.changeVolume(-5 * symbolAmount);
    
    setMessage("Volume: " + this.player.getVolume() + "%");
    notifyWorker();
  }

}
