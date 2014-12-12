package be.iswleuven.mediacontroller.plugin.admin;

import be.iswleuven.mediacontroller.admin.AdminHandler;
import be.iswleuven.mediacontroller.admin.FailedLoginException;
import be.iswleuven.mediacontroller.command.Command;
import be.iswleuven.mediacontroller.command.CommandException;

import com.google.inject.Inject;

public class LoginCommand extends Command {

  /**
   * The command string.
   */
  public static final String COMMAND_STRING = "login";
  
  /**
   * The command help string.
   */
  public static final String COMMAND_HELP = " <password>\tLog in met het opgegeven wachtwoord.";
  
  /**
   * The admin handler instance.
   */
  private final AdminHandler adminHandler;
  
  /**
   * Create a new login command.
   * 
   * @param adminHandler
   * @param plugin
   */
  @Inject
  public LoginCommand(AdminHandler adminHandler, AdminPlugin plugin) {
    super(plugin);
    this.adminHandler = adminHandler;
  }

  @Override
  public void execute() throws CommandException {
    try {
      adminHandler.logIn(getParameters()[0], getWorker());
      setMessage("Ingelogd!");
      notifyWorker();
    } catch (FailedLoginException e) {
      throw new CommandException(e.getMessage());
    }
  }

}
