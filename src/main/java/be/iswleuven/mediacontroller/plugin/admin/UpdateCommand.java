package be.iswleuven.mediacontroller.plugin.admin;

import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;
import be.iswleuven.mediacontroller.dependency.Dependency;
import be.iswleuven.mediacontroller.dependency.DependencyException;
import be.iswleuven.mediacontroller.dependency.DependencyHandler;

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
   * The dependency handler instance.
   */
  public final DependencyHandler dependencyHandler;
  
  /**
   * Create a new update command.
   * 
   * @param plugin
   */
  @Inject
  public UpdateCommand(AdminPlugin plugin, DependencyHandler dependencyHandler) {
    super(plugin);
    this.dependencyHandler = dependencyHandler;
  }

  @Override
  public void execute() throws CommandException {
    try {
      for (Dependency dependency : this.dependencyHandler.getDependencies()) {
        if (! dependency.isInstalled()) {
          dependency.install();
          setMessage(dependency.getOutput());
          notifyWorker();
        }
        
        if (dependency.isOutdated()) {
          dependency.update();
          setMessage(dependency.getOutput());
          notifyWorker();
        }
      }
    } catch (DependencyException e) {
      throw new CommandException(e.getMessage());
    }
  }

}
