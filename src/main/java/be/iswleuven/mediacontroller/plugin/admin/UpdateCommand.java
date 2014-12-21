package be.iswleuven.mediacontroller.plugin.admin;

import be.iswleuven.mediacontroller.admin.AdminHandler;
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
  public static final String COMMAND_HELP = "\t\tUpdate de mediacontroller en de dependencies.";
  
  /**
   * The admin handler instance.
   */
  private final AdminHandler adminHandler;
  
  /**
   * The dependency handler instance.
   */
  private final DependencyHandler dependencyHandler;
  
  /**
   * Create a new update command.
   * 
   * @param adminHandler
   * @param plugin
   * @param dependencyHandler
   */
  @Inject
  public UpdateCommand(AdminHandler adminHandler, AdminPlugin plugin, DependencyHandler dependencyHandler) {
    super(plugin);
    this.adminHandler = adminHandler;
    this.dependencyHandler = dependencyHandler;
  }

  @Override
  public void execute() throws CommandException {
    if (!adminHandler.isAdmin(getWorker())) {
      throw new NoAdminException();
    }
    
    try {
      for (Dependency dependency : this.dependencyHandler.getDependencies()) {
        if (! dependency.isInstalled()) {
          dependency.install();
          setMessage(dependency.getOutput());
        }
        
        if (dependency.isOutdated()) {
          dependency.update();
          setMessage(dependency.getOutput());
        }
      }
    } catch (DependencyException e) {
      throw new CommandException(e.getMessage());
    }
  }

}
