package be.iswleuven.mediacontroller.plugin.standard;

import be.iswleuven.mediacontroller.command.Command;

import com.google.inject.Inject;

public class HelpCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "help";
  
  /**
   * Create a new standard help command.
   * 
   * @param plugin
   */
  @Inject
  public HelpCommand(StandardPlugin plugin) {
    super(plugin);
  }
  
  @Override
  public void execute() {
    String output = this.PLUGIN + "\n";
    output += "Beschikbare commando's zijn:\n";
    output += "  next\t\t\tSpeel volgend liedje in de playlist. Stop indien er geen meer zijn.\n";
    output += "  pause\t\t\tPauzeer het liedje. Gebruik dit commando nogmaals om weer te spelen.\n";
    output += "  skip <getal[s|m]>\tSla een deel van het huidig liedje over. Standaard eenheid is seconden.\n"
        + "\t\t\tMogelijk notation zijn: 30, 30s, 1m, 1m30s, 1m30\n";
    output += "  stop\t\t\tStop het huidig liedje en maak de afspeellijst leeg.\n";
    output += "  vol | volume <getal>\tZet het volume op het gegeven niveau. Niveaus hoger dan 100 gaan naar 100.\n";
    output += "  +\t\t\tZet het volume 5% hoger. 5% extra per extra plus.\n";
    output += "  -\t\t\tZet he volume 5% lager. 5% exta per extra min.\n";
    output += "  help\t\t\tToon dit help menu.";
    
    setMessage(output);
    notifyWorker();
  }
  
}
