package be.iswleuven.mediacontroller.plugin.radiotunes;

import be.iswleuven.mediacontroller.plugin.Plugin;

public class RadioTunesPlugin extends Plugin {

  /**
   * Create a new radio tunes plugin.
   */
  public RadioTunesPlugin() {
    super("RadioTunesPlugin", "1.0.1", "rt", "Yuri Moens");
  }

  @Override
  public void initializeCommands() {
    registerCommand(AddCommand.class);
    registerCommand(ListCommand.class);
  }

}
