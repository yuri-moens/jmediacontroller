package be.iswleuven.mediacontroller.plugin.rockradio;

import be.iswleuven.mediacontroller.plugin.Plugin;

public class RockRadioPlugin extends Plugin {

  /**
   * Create a new rock radio plugin.
   */
  public RockRadioPlugin() {
    super("RockRadio", "1.0", "rr", "Yuri Moens");
  }

  @Override
  public void initializeCommands() {
    registerCommand(AddCommand.class);
    registerCommand(HelpCommand.class);
  }

}
