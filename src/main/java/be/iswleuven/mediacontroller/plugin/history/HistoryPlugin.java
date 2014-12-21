package be.iswleuven.mediacontroller.plugin.history;

import be.iswleuven.mediacontroller.plugin.Plugin;

public class HistoryPlugin extends Plugin {

  /**
   * Create a new history plugin.
   */
  public HistoryPlugin() {
    super("HistoryPlugin", "1.0.1", "history", "Yuri Moens");
  }

  @Override
  public void initializeCommands() {
    registerCommand(HistoryCommand.class);
    registerCommand(TopCommand.class);
  }

}
