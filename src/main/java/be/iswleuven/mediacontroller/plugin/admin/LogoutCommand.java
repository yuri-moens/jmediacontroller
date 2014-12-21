package be.iswleuven.mediacontroller.plugin.admin;

import be.iswleuven.mediacontroller.admin.AdminHandler;
import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;

import com.google.inject.Inject;

public class LogoutCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "logout";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = "\t\tLogout als admin.";
  
  /**
   * The admin handler instance.
   */
  private final AdminHandler adminHandler;
  
  @Inject
  public LogoutCommand(AdminHandler adminHandler, AdminPlugin plugin) {
    super(plugin);
    this.adminHandler = adminHandler;
  }

  @Override
  public void execute() throws CommandException {
    if (!adminHandler.isAdmin(getWorker())) {
      throw new NoAdminException();
    }
    
    adminHandler.logOut();
    
    setMessage("Uitgelogd!");
  }

}
