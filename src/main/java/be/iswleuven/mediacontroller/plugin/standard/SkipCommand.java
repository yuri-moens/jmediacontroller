package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Player;

import com.google.inject.Inject;

public class SkipCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "skip";
  
  /**
   * The player instance.
   */
  private final Player player;
  
  /**
   * Create a new skip command.
   * 
   * @param player
   * @param plugin
   */
  @Inject
  public SkipCommand(Player player, StandardPlugin plugin) {
    super(plugin);
    this.player = player;
  }

  @Override
  public void execute() throws CommandException {
    String parameter = getParameters()[0];
    long delta;
    
    try {
      if (parameter.contains("s") && parameter.contains("m")) { // 1m30s
        delta = Long.parseLong(parameter.substring(0, parameter.indexOf("m"))) * 1000 * 60;
        delta += Long.parseLong(parameter.substring(parameter.indexOf("m") + 1, parameter.length() - 1)) * 1000;
      } else if (parameter.endsWith("s")) { // 30s
        delta = Long.parseLong(parameter.substring(0, parameter.length() - 1)) * 1000;
      } else if (parameter.endsWith("m")) { // 1m
        delta = Long.parseLong(parameter.substring(0, parameter.length() - 1)) * 1000 * 60;
      } else if (parameter.contains("m")) { // 1m30
        delta = Long.parseLong(parameter.substring(0, parameter.indexOf("m"))) * 1000 * 60;
        delta += Long.parseLong(parameter.substring(parameter.indexOf("m") + 1, parameter.length())) * 1000;
      } else { // 30
        delta = Long.parseLong(parameter) * 1000;
      }
    } catch (Exception e) {
      throw new CommandException("Delta moet een getal zijn met optioneel s of m om seconden en minuten aan te duiden.");
    }
    
    this.player.skip(delta);
  }

}
