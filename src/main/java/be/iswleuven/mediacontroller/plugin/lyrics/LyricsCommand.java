package be.iswleuven.mediacontroller.plugin.lyrics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.player.Player;

import com.google.inject.Inject;

public class LyricsCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "default";

  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "Geef de lyrics van het huidige liedje.";
  
  /**
   * The player instance.
   */
  private final Player player;
  
  /**
   * Create a new lyrics command.
   * 
   * @param plugin
   * @param player
   */
  @Inject
  public LyricsCommand(LyricsPlugin plugin, Player player) {
    super(plugin);
    this.player = player;
  }

  @Override
  public void execute() throws CommandException {
    String songTitle = this.player.getCurrentlyPlaying();
    
    try {
      String lyricsPage = getLyricsLink(songTitle);
      String lyrics = getLyricsFromPage(lyricsPage);
      
      setMessage(lyrics);
      notifyWorker();
    } catch (Exception e) {
      throw new CommandException("Fout bij het zoeken van de lyrihelpcs.");
    }
  }
  
  /**
   * Get the link to the lyrics page of the given song using azlyrics.com.
   * 
   * @param songTitle
   * @return
   * @throws IOException
   */
  private String getLyricsLink(String songTitle) throws IOException {
    songTitle = songTitle.replaceAll(" *- *", " ");
    URL queryUrl = new URL("http://search.azlyrics.com/search.php?q=" + URLEncoder.encode(songTitle, "UTF-8"));

    BufferedReader in = new BufferedReader(new InputStreamReader(queryUrl.openStream()));
    
    String input;
    String lyricsLink = null;
    
    while ((input = in.readLine()) != null) {
      if (input.startsWith("1. <a href=")) {
        lyricsLink = input.replaceFirst("(.*)href=\"", "");
        lyricsLink = lyricsLink.replaceAll("html(.*)", "html");
      }
    }
    
    return lyricsLink;
  }
  
  /**
   * Get the lyrics from the lyrics page at azlyrics.com.
   * 
   * @param pageUrl
   * @return
   * @throws IOException
   */
  private String getLyricsFromPage(String pageUrl) throws IOException {
    URL queryUrl = new URL(pageUrl);
    
    BufferedReader in = new BufferedReader(new InputStreamReader(queryUrl.openStream()));
    
    String input;
    String lyrics = "";
    boolean atLyrics = false;
    
    while ((input = in.readLine()) != null) {
      if (!atLyrics && input.contains("start of lyrics")) {
        atLyrics = true;
        continue;
      }
      
      if (atLyrics && input.contains("end of lyrics")) {
        atLyrics = false;
        break;
      }
      
      if (atLyrics) {
        lyrics += input;
      }
    }
    
    lyrics = lyrics.replaceAll("<br />", "\n");
    
    return lyrics;
  }

}
