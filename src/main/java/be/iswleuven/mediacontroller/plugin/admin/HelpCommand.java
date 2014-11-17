package be.iswleuven.mediacontroller.plugin.admin;

import be.iswleuven.mediacontroller.command.Command;

public class HelpCommand extends Command {
  
  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "help";

  @Override
  public void execute() {
    notifyObservers("Hello world from help command!");
  }

}
