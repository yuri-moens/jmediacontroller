package be.iswleuven.mediacontroller.plugin;

public class PluginNotFoundException extends Exception {

  /**
   * The serial ID.
   */
  private static final long serialVersionUID = 5970416835649480221L;

  public PluginNotFoundException(String plugin) {
    super("Plugin with name \"" + plugin + "\" not found.");
  }
  
}
