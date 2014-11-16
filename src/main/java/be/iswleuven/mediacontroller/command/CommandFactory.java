package be.iswleuven.mediacontroller.command;

import be.iswleuven.mediacontroller.util.Observer;

public class CommandFactory {

  public Command createCommand(String rawCommand, Observer obs) {
    String[] commandArray = rawCommand.split(" ");
    
    for (String s : commandArray) {
      System.out.println(s);
    }
    
    return null;
  }
  
}
