package be.iswleuven.mediacontroller.plugin.radiotunes;

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
  public static final String COMMAND_HELP ="<name>";
  
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
  public AddCommand(CommandBus commandBus, RadioTunesPlugin plugin) {
    super(plugin);
    this.commandBus = commandBus;
  }

  @Override
  public void execute() throws CommandException {
    String parameter = getParameters()[0];

    String url = "http://listen.radiotunes.com/public1/" + parameter + ".pls";
    
    commandBus.send("radio " + url, this.worker);
  }
  
}
