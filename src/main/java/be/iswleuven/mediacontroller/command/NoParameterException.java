package be.iswleuven.mediacontroller.command;

public class NoParameterException extends CommandException {

  /**
   * The serial ID.
   */
  private static final long serialVersionUID = -3902117596600213522L;

  /**
   * Create a new no parameter exception.
   * @param msg
   */
  public NoParameterException() {
    super("Geef een parameter mee.");
  }

}
