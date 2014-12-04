package be.iswleuven.mediacontroller.plugin.jplaylist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandBus;
import be.iswleuven.mediacontroller.command.CommandException;

import com.google.inject.Inject;

public class LoadCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "default";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "<jpl> [amount] [true|false]\tLaad de liedjes in de gegeven playlist in. Specifieer een optionele hoeveelheid en of de playlist moet geshuffled worden. (standaard true)";
  
  /**
   * The command bus instance.
   */
  private final CommandBus commandBus;
  
  /**
   * Create a new play command.
   * 
   * @param commandBus
   * @param plugin
   */
  @Inject
  public LoadCommand(CommandBus commandBus, JPlaylistPlugin plugin) {
    super(plugin);
    this.commandBus = commandBus;
  }

  @Override
  public void execute() throws CommandException {
    String fileName = getParameters()[0];
    int amount = Integer.MAX_VALUE;
    boolean randomize = true;
    
    if (getParameters().length == 2) {
      try {
        amount = Integer.parseInt(getParameters()[1]);
      } catch (NumberFormatException e) {
        randomize = getParameters()[1].equals("true");
      }
    } else if (getParameters().length == 3) {
      try {
        amount = Integer.parseInt(getParameters()[1]);
        randomize = getParameters()[2].equals("true");
      } catch (NumberFormatException e) {
        amount = Integer.parseInt(getParameters()[2]);
        randomize = getParameters()[1].equals("true");
      }
    }
    
    File file = new File(System.getProperty("user.home") + "/.mediacontroller/playlists/" + fileName + ".jpl");
    
    if (! file.exists()) {
      throw new CommandException("Playlist niet gevonden.");
    }
    
    List<String> playlist = null;
    
    try {
      playlist = readPlaylistFile(file, amount, randomize);
      
      for (String song : playlist) {
        this.commandBus.send("yt " + song, worker);
      }
    } catch (Exception e) {
      throw new CommandException("Fout bij het lezen van het playlist bestand.");
    }
  }
  
  /**
   * Return a list of song titles or URLs.
   * 
   * @return
   * @throws IOException 
   */
  private List<String> readPlaylistFile(File file, int amount, boolean randomize) throws IOException {
    List<String> playlist = new ArrayList<String>();
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line = null;
    int i = 0;
    
    while ((line = reader.readLine()) != null && i < amount) {
      playlist.add(line);
      i++;
    }
    
    reader.close();
    
    if (randomize) {
      Collections.shuffle(playlist);
    }
    
    return playlist;
  }
  
}
