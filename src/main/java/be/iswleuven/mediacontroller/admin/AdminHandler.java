package be.iswleuven.mediacontroller.admin;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Observable;
import java.util.Observer;

import javax.xml.bind.DatatypeConverter;

import be.iswleuven.mediacontroller.config.Config;
import be.iswleuven.mediacontroller.server.Worker;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AdminHandler implements Observer {

  /**
   * The hashed admin password.
   */
  private final String passwordHash;
  
  /**
   * The logged in server worker.
   */
  private Worker loggedInWorker;
  
  /**
   * Create a new admin handler.
   */
  @Inject
  public AdminHandler(Config config) {
    this.passwordHash = config.getPassword();
  }
  
  /**
   * Try to login the given worker by checking the given password.
   * Throws a FailedLoginException when a wrong password is given.
   * 
   * @param password
   * @param worker
   * @throws FailedLoginException
   */
  public void logIn(String password, Worker worker) throws FailedLoginException {
    String passwordHash = getHash(password);
    
    if (this.passwordHash.equals(passwordHash)) {
      this.loggedInWorker = worker;
      worker.addObserver(this);
    } else {
      throw new FailedLoginException();
    }
  }
  
  /**
   * Log the current worker out.
   */
  public void logOut() {
    this.loggedInWorker = null;
  }
  
  /**
   * Check if a worker is logged in.
   * 
   * @return
   */
  public boolean isLoggedIn() {
    return this.loggedInWorker != null;
  }
  
  /**
   * Check if the given worker is logged in as admin.
   * 
   * @param worker
   * @return
   */
  public boolean isAdmin(Worker worker) {
    return this.loggedInWorker == worker;
  }

  /**
   * Log the user out if the worker changes state.
   */
  @Override
  public void update(Observable o, Object obs) {
    logOut();
  }
  
  /**
   * Return the SHA-256 hash from the given password.
   * 
   * @param password
   * @return
   */
  private String getHash(String password) {
    String output = null;
    
    try {
      MessageDigest sha = MessageDigest.getInstance("SHA-256");
      sha.update(password.getBytes("UTF-8"));
      byte[] digest = sha.digest();
      
      output = DatatypeConverter.printHexBinary(digest).toLowerCase();
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    
    return output;
  }
  
}
