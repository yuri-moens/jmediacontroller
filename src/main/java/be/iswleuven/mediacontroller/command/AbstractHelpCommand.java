package be.iswleuven.mediacontroller.command;

import java.lang.reflect.Field;

import be.iswleuven.mediacontroller.plugin.Plugin;

public abstract class AbstractHelpCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "help";
  
  /**
   * Create a new help command.
   * 
   * @param player
   * @param plugin
   */
  public AbstractHelpCommand(Plugin plugin) {
    super(plugin);
  }

  @Override
  public void execute() throws CommandException {
    try {
      String output = this.PLUGIN.toString() + "\n\nBeschikbare commando's:\n";
      
      for (String value : this.PLUGIN.getCommands().keySet()) {        
        Class<? extends Command> commandClass = this.PLUGIN.getCommands().get(value);
        
        Field field = null;
        String commandHelp = "  " + value;
        
        try {
          field = commandClass.getDeclaredField("COMMAND_ALIASES");
          String[] aliases = (String[]) field.get(null);
          
          for (String alias : aliases) {
            commandHelp += " | " + alias;
          }
        } catch (Exception e) {}
        
        field = commandClass.getDeclaredField("COMMAND_HELP");
        commandHelp += (String) field.get(null);
        
        output += commandHelp + "\n";
      }
      
      setMessage(output);
      notifyWorker();
    } catch (Exception e) {
      throw new CommandException("Geen command help string gevonden.");
    }
  }
  
}