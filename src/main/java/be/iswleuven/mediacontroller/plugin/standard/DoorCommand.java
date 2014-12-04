package be.iswleuven.mediacontroller.plugin.standard;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;

import com.google.inject.Inject;

public class DoorCommand extends Command {

  /**
   * The command string.
   */
  public static String COMMAND_STRING = "o";
  
  /**
   * The command aliases.
   */
  public static String[] COMMAND_ALIASES = { "O", "deur", "sesam", "openu" };

  /**
   * The command help string.
   */
  public static String COMMAND_HELP = "Doe 'o', dit opent de ISW deur";

  /**
   * Create a new door command.
   * 
   * @param plugin
   */
  @Inject
  public DoorCommand(StandardPlugin plugin) {
    super(plugin);
  }

  @Override
  public void execute() throws CommandException {
    try {
      Socket client = new Socket("d.isw", 3333);
      
      PrintWriter out = new PrintWriter(client.getOutputStream(), true);
      out.println("o");
      out.close();
      
      client.close();
      
      setMessage("Deur geopend");
      notifyWorker();
    } catch (IOException e) {
      throw new CommandException("Deur kon niet gevonden worden of er kon niet heen geschreven worden.");
    }
  }

}
