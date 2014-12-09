package be.iswleuven.mediacontroller.plugin.radio;

import be.iswleuven.mediacontroller.plugin.Plugin;

public class RadioPlugin extends Plugin {

  /**
   * Create a new radio plugin.
   */
  public RadioPlugin() {
    super("RadioPlugin", "1.0.0", "radio", "Yuri Moens");
  }

  @Override
  public void initializeCommands() {
    registerCommand(AddCommand.class);
  }

}
