package be.iswleuven.mediacontroller.plugin.history;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.History;
import be.iswleuven.mediacontroller.util.ValueComparator;

import com.google.inject.Inject;

public class TopCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "top";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = " [month] [amount]";
  
  /**
   * The history.
   */
  private final History history;
  
  /**
   * The map with month translations.
   */
  private static final Map<String, String> monthTranslations;
  static {
    monthTranslations = new HashMap<String, String>();

    monthTranslations.put("januari", "01-january");
    monthTranslations.put("january", "01-january");
    monthTranslations.put("februari", "02-february");
    monthTranslations.put("february", "02-february");
    monthTranslations.put("maart", "03-march");
    monthTranslations.put("march", "03-march");
    monthTranslations.put("april", "04-april");
    monthTranslations.put("mei", "05-may");
    monthTranslations.put("may", "05-may");
    monthTranslations.put("juni", "06-june");
    monthTranslations.put("june", "06-june");
    monthTranslations.put("juli", "07-july");
    monthTranslations.put("july", "07-july");
    monthTranslations.put("augustus", "08-august");
    monthTranslations.put("august", "08-august");
    monthTranslations.put("september", "09-september");
    monthTranslations.put("oktober", "10-october");
    monthTranslations.put("october", "10-october");
    monthTranslations.put("november", "11-november");
    monthTranslations.put("december", "12-december");
  }
  
  /**
   * Create a new top command.
   * 
   * @param history
   * @param plugin
   */
  @Inject
  public TopCommand(History history, HistoryPlugin plugin) {
    super(plugin);
    this.history = history;
  }

  @Override
  public void execute() throws CommandException {
    int amount = 10;
    String month = "all";
    
    switch (getParameters().length) {
      case 1: try {
        amount = Integer.parseInt(getParameters()[0]);
      } catch (NumberFormatException e) {
        month = getMonth(getParameters()[0]);
      }
        break;
      case 2: try {
        amount = Integer.parseInt(getParameters()[0]);
        month = getMonth(getParameters()[1]);
      } catch (NumberFormatException e) {
        month = getMonth(getParameters()[0]);
        amount = Integer.parseInt(getParameters()[1]);
      }
        break;
    }
    
    try {
      Set<String> topSet = getTopSet(month, amount);
      String output = "";
      int i = 0;
      
      for (String entry : topSet) {
        if (i == amount) {
          break;
        }
        i++;
        
        output += i + ". " + entry + "\n";
      }
      
      setMessage(output);
    } catch (FileNotFoundException e) {
      throw new CommandException("Geen geschiedenis gevonden.");
    }
  }
  
  /**
   * Get the month in English.
   * 
   * @param month
   * @return
   */
  private String getMonth(String month) {
    if (TopCommand.monthTranslations.get(month) == null) {
      return month;
    } else {
      return TopCommand.monthTranslations.get(month);
    }
  }
  
  /**
   * Get the top set from the given month.
   * 
   * @param month
   * @param amount
   * @return
   * @throws FileNotFoundException
   */
  private Set<String> getTopSet(String month, int amount) throws FileNotFoundException {
    List<String> history;
    
    if (month.equals("all")) {
      history = this.history.getAllHistory();
    } else {
      history = this.history.getHistory(month);
    }
    
    Map<String, Integer> topMap = new HashMap<String, Integer>();
    
    for (String entry : history) {
      if (topMap.containsKey(entry)) {
        int i = topMap.get(entry);
        topMap.put(entry, ++i);
      } else {
        topMap.put(entry, 1);
      }
    }
    
    SortedMap<String, Integer> sortedTopMap = new TreeMap<String, Integer>(new ValueComparator(topMap));
    
    sortedTopMap.putAll(topMap);
    
    return sortedTopMap.keySet();
  }

}
