package be.iswleuven.mediacontroller.plugin.history;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.History;

import com.google.inject.Inject;

public class HistoryCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "default";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = " <amount|month [year]>";
  
  /**
   * The map with month translations.
   */
  private static final Map<String, String> monthTranslations;
  static {
    monthTranslations = new HashMap<String, String>();

    monthTranslations.put("januari", "january");
    monthTranslations.put("februari", "february");
    monthTranslations.put("maart", "march");
    monthTranslations.put("april", "april");
    monthTranslations.put("mei", "may");
    monthTranslations.put("juni", "june");
    monthTranslations.put("juli", "july");
    monthTranslations.put("augustus", "august");
    monthTranslations.put("september", "september");
    monthTranslations.put("oktober", "october");
    monthTranslations.put("november", "november");
    monthTranslations.put("december", "december");
  }
  
  /**
   * The history.
   */
  private final History history;
  
  /**
   * Create a new history command.
   * 
   * @param plugin
   */
  @Inject
  public HistoryCommand(History history, HistoryPlugin plugin) {
    super(plugin);
    this.history = history;
  }

  @Override
  public void execute() throws CommandException {
    List<String> history = null;

    try {
      if (getParameters().length == 2) {
        history = handleMonth(getParameters()[0].toLowerCase(), getParameters()[1]);
      } else {
        try {
          history = handleAmount(Integer.parseInt(getParameters()[0]));
        } catch (NumberFormatException ee) {
          history = handleMonth(getParameters()[0].toLowerCase(), null);
        }
      }
    } catch (FileNotFoundException e) {
      throw new CommandException("Geen geschiedenis gevonden.");
    }
    
    String output = "";
    
    for (String song : history) {
      output += song + "\n";
    }
    
    setMessage(output);
    notifyWorker();
  }
  
  /**
   * Get the given amount of songs from the history.
   * 
   * @param amount
   * @return
   */
  private List<String> handleAmount(int amount) throws FileNotFoundException {
    List<String> history = this.history.getAllHistory();
    
    if (amount < history.size()) {
      history = history.subList(history.size() - amount, history.size());
    }
    
    return history;
  }
  
  /**
   * Get the history for the given month.
   * 
   * @param month
   * @param year
   * @return
   * @throws FileNotFoundException
   */
  private List<String> handleMonth(String month, String year) throws FileNotFoundException {
    if (HistoryCommand.monthTranslations.get(month) != null) {
      month = HistoryCommand.monthTranslations.get(month);
    }
    
    if (year == null) {
      return this.history.getHistory(month);
    } else {
      return this.history.getHistory(month, year);
    }
  }

}
