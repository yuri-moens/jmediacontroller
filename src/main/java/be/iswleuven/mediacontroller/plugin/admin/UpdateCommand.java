package be.iswleuven.mediacontroller.plugin.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.plugin.youtube.YoutubePlugin;

import com.google.inject.Inject;

public class UpdateCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "update";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "\tUpdate de mediacontroller en de dependencies.";
  
  /**
   * Create a new update command.
   * 
   * @param plugin
   */
  @Inject
  public UpdateCommand(AdminPlugin plugin) {
    super(plugin);
  }

  @Override
  public void execute() throws CommandException {
    File dependenciesDir = new File(System.getProperty("user.home") + "/.mediacontroller/dependencies/");
    
    if (! dependenciesDir.exists()) {
      setMessage("Dependencies directory aanmaken...");
      notifyWorker();
      dependenciesDir.mkdirs();
    }
    
    if (shouldInstallYoutubeDl()) {
      setMessage("youtube-dl niet gevonden, wordt gedownload...");
      notifyWorker();
      
      installYoutubeDl();
      
      setMessage("youtube-dl werd geïnstalleerd.");
      notifyWorker();
    } else {
      String output = updateYoutubeDl();
      
      setMessage("[youtube-dl] " + output);
      notifyWorker();
    }
  }
  
  /**
   * Check if youtube-dl should be installed.
   * 
   * @return
   */
  private boolean shouldInstallYoutubeDl() {    
    File file = new File(YoutubePlugin.YOUTUBE_DL);
    
    return !file.exists();
  }
  
  /**
   * Install the youtube-dl dependency.
   * 
   * @throws CommandException
   */
  private void installYoutubeDl() throws CommandException {
    File file = new File(YoutubePlugin.YOUTUBE_DL);
    
    try {
      URL youtubeDlUrl = new URL("https://yt-dl.org/downloads/latest/youtube-dl");
      
      ReadableByteChannel rbc = Channels.newChannel(youtubeDlUrl.openStream());
      
      FileOutputStream fos = new FileOutputStream(file);
      
      fos.getChannel().transferFrom(rbc, 0, Integer.MAX_VALUE);
      
      fos.close();
    } catch (Exception e) {
      throw new CommandException("youtube-dl kon niet gedownload worden.");
    }
  }
  
  /**
   * Update youtube-dl.
   */
  private String updateYoutubeDl() {
    String output = null;
    
    try {
      Process p = Runtime.getRuntime().exec("python " + YoutubePlugin.YOUTUBE_DL + " -U");

      BufferedReader outputReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
      
      output = outputReader.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return output;
  }

}
