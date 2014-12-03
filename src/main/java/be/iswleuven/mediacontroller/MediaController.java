package be.iswleuven.mediacontroller;

import java.io.File;

import be.iswleuven.mediacontroller.command.CommandHandler;
import be.iswleuven.mediacontroller.config.ConfigLoader;
import be.iswleuven.mediacontroller.dependency.DependencyHandler;
import be.iswleuven.mediacontroller.player.Player;
import be.iswleuven.mediacontroller.plugin.PluginHandler;
import be.iswleuven.mediacontroller.server.ServerHandler;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class MediaController {
  
  /**
   * The version of the MediaController.
   */
  public static final String VERSION = "0.1 beta";
  
  /**
   * Specify if the controller should be verbose.
   */
  public static boolean verbose = false;
  
  /**
   * Start the mediacontroller.
   * 
   * @param args
   */
  public static void main(String[] args) {
    if (args != null) {
      parseArgs(args);
    }
    
    Injector injector = Guice.createInjector(new AppModule());
    
    injector.getInstance(ServerHandler.class).startAllServers();
    injector.getInstance(PluginHandler.class);
    injector.getInstance(CommandHandler.class);
    injector.getInstance(DependencyHandler.class);
    injector.getInstance(Player.class);
  }
  
  /**
   * Parse the command line arguments.
   * 
   * @param args
   */
  private static void parseArgs(String[] args) {
    for (String arg : args) {
      if (arg.contains("-c=") || arg.contains("--config=")) {
        if (arg.lastIndexOf("=") == arg.length() - 1) {
          System.out.println("Geef een configuratiebestand.\n");
          printHelp();
        }
        
        String path = arg.substring(arg.lastIndexOf("=") + 1);
        path = path.replace("~", System.getProperty("user.home"));
        ConfigLoader.load(new File(path));
        
        continue;
      }
      
      switch (arg) {
        case "-h":
        case "--help": printHelp();
          break;
        case "-v":
        case "--verbose": verbose = true;
          break;
        default:
          System.out.println("Argument \"" + arg + "\" bestaat niet.\n");
          printHelp();
      }
    }
  }
  
  /**
   * Print the help message with the possible arguments.
   */
  private static void printHelp() {
    System.out.println("MediaController versie " + VERSION + "\n");
    
    System.out.println("Mogelijke argumenten zijn:\n");
    System.out.println("  -c=FILE, --config=FILE\tEen specifiek configuratiebestand inladen.");
    System.out.println("  -h, --help\t\t\tDit help menu tonen.");
    System.out.println("  -v, --verbose\t\t\tOutput naar console.");
    
    System.exit(0);
  }

}
