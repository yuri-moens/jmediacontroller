package be.iswleuven.mediacontroller.plugin.admin;

import be.iswleuven.mediacontroller.command.CommandException;

public class NoAdminException extends CommandException {

  /**
   * The serial id.
   */
  private static final long serialVersionUID = -3560140084100658178L;

  /**
   * Create a new no admin exception.
   */
  public NoAdminException() {
    super("Je bent geen admin. Probeer eerst in te loggen.");
  }

}
