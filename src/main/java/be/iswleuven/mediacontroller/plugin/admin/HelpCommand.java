package be.iswleuven.mediacontroller.plugin.admin;

import be.iswleuven.mediacontroller.command.Command;

import com.google.inject.Inject;

public class HelpCommand extends Command {
  
  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "help";

  /**
   * Create a new admin help command.
   * 
   * @param plugin
   */
  @Inject
  public HelpCommand(AdminPlugin plugin) {
    super(plugin);
  }
  
  @Override
  public void execute() {
    String output = this.PLUGIN + "\n";
    output += "Beschikbare commando's zijn:\n";
    output += "\tlist - Geef een lijst van alle beschikbare servers.\n";
    output += "\tstart|stop|restart <server name|all> - Start, stop of herstart een/alle server(s)\n";
    output += "\thelp - Toon dit help menu.";
    
    setMessage(output);
    notifyWorker();
  }

}
