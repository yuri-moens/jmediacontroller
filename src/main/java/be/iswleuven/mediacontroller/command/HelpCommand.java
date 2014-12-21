package be.iswleuven.mediacontroller.command;

import java.lang.reflect.Field;

import be.iswleuven.mediacontroller.plugin.Plugin;

public class HelpCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "help";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "";
  
  /**
   * Create a new help command.
   * 
   * @param plugin
   */
  public HelpCommand(Plugin plugin) {
    super(plugin);
  }

  @Override
  public void execute() throws CommandException {
    try {
      String output = this.plugin.toString() + "\n\nBeschikbare commando's:\n";
      
      for (String value : this.plugin.getCommands().keySet()) {        
        Class<? extends Command> commandClass = this.plugin.getCommands().get(value);
        
        Field field = null;
        String commandHelp = value.equals("default") ? "  " : "  " + value;
        
        try {
          field = commandClass.getDeclaredField("COMMAND_ALIASES");
          String[] aliases = (String[]) field.get(null);
          
          boolean isAlias = false;
          
          for (String alias : aliases) {
            commandHelp += " | " + alias;
            if (alias.equals(value)) {
              isAlias = true;
            }
          }
          
          if (isAlias) {
            continue;
          }
        } catch (Exception e) {}
        
        field = commandClass.getDeclaredField("COMMAND_HELP");
        commandHelp += (String) field.get(null);
        
        output += commandHelp + "\n";
      }
      
      setMessage(output);
    } catch (Exception e) {
      throw new CommandException("Geen command help string gevonden.");
    }
  }
  
}