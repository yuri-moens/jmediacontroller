package be.iswleuven.mediacontroller.plugin.rockradio;

import java.util.HashMap;
import java.util.Map;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandBus;
import be.iswleuven.mediacontroller.command.CommandException;

import com.google.inject.Inject;

public class AddCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "default";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP ="<name>\tMogelijke stationnamen zijn te vinden op rockradio.com.\n"
      + "\t\tMogelijke aliassen zijn:\n"
      + "\t\t  60s = 60srock\n"
      + "\t\t  80s = 80srock\n"
      + "\t\t  80salt = 80salternative\n"
      + "\t\t  90s = 90srock\n"
      + "\t\t  90salt = 90salternative\n"
      + "\t\t  ballads = rockballads\n"
      + "\t\t  beatles = beatlestribute\n"
      + "\t\t  blues = bluesrock\n"
      + "\t\t  death = deathmetal\n"
      + "\t\t  indie = indierock\n"
      + "\t\t  melodeath = melodicdeathmetal\n"
      + "\t\t  thrash = thrashmetal";
  
  /**
   * The map with station aliases.
   */
  private static final Map<String, String> stationAliases;
  static
  {
    stationAliases = new HashMap<String, String>();
    stationAliases.put("60s", "60srock");
    stationAliases.put("80s", "80srock");
    stationAliases.put("80salt", "80salternative");
    stationAliases.put("90s", "90srock");
    stationAliases.put("90salt", "90salternative");
    stationAliases.put("ballads", "rockballads");
    stationAliases.put("beatles", "beatlestribute");
    stationAliases.put("blues", "bluesrock");
    stationAliases.put("death", "deathmetal");
    stationAliases.put("indie", "indierock");
    stationAliases.put("melodeath", "melodicdeathmetal");
    stationAliases.put("thrash", "thrashmetal");
  }
  
  /**
   * The command bus instance.
   */
  private final CommandBus commandBus;
  
  /**
   * Create a new play command.
   * 
   * @param plugin
   */
  @Inject
  public AddCommand(CommandBus commandBus, RockRadioPlugin plugin) {
    super(plugin);
    this.commandBus = commandBus;
  }

  @Override
  public void execute() throws CommandException {
    String parameter = getParameters()[0];
    
    parameter = AddCommand.stationAliases.get(parameter) == null ?
        parameter : AddCommand.stationAliases.get(parameter);

    String url = "http://listen.rockradio.com/public3/" + parameter + ".pls";
    
    commandBus.send("radio " + url, this.worker);
  }
  
}
