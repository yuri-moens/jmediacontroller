package be.iswleuven.mediacontroller.plugin.radiotunes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;

import com.google.inject.Inject;

public class ListCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "list";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "\t\tGeef een lijst van de radiostations.";
  
  /**
   * Create a new radio tunes plugin.
   * 
   * @param plugin
   */
  @Inject
  public ListCommand(RadioTunesPlugin plugin) {
    super(plugin);
  }

  @Override
  public void execute() throws CommandException {
    try {
      List<JSONObject> jsonObjects = parseToJson(getRawJson());
      
      String output = "";
      
      for (JSONObject json : jsonObjects) {
        output += json.getString("key") + ": " + json.getString("name") + "\n";
      }
      
      setMessage(output);
      notifyWorker();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Get the raw json from RadioTunes.
   * 
   * @return
   * @throws Exception
   */
  private String getRawJson() throws Exception {
    URL rtUrl = new URL("http://listen.radiotunes.com/public1/");
    String rawJson = "";
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(rtUrl.openStream()));
    String line = null;
    
    while ((line = reader.readLine()) != null) {
      rawJson += line;
    }
    
    reader.close();
    
    return rawJson;
  }
  
  private List<JSONObject> parseToJson(String rawJson) throws JSONException {
    rawJson = rawJson.replace("[", "");
    rawJson = rawJson.replace("]", "");
    rawJson = rawJson.replace("},{", "}%{");
    
    String[] rawJsonArray = rawJson.split("%");
    List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
    
    for (String raw : rawJsonArray) {
      jsonObjects.add(new JSONObject(raw));
    }
    
    return jsonObjects;
  }

}
