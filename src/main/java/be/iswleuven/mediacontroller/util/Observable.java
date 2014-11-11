package be.iswleuven.mediacontroller.util;

import java.util.ArrayList;
import java.util.List;

public class Observable {

  /**
   * The list of observers.
   */
  private List<Observer> observers;
  
  /**
   * Create a new observable object.
   */
  public Observable() {
    observers = new ArrayList<Observer>();
  }
  
  /**
   * Register an observer to this observable.
   * 
   * @param obs
   * @return
   */
  public boolean registerObserver(Observer obs) {
    if (! observers.contains(obs)) {
      observers.add(obs);
      
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Remove an observer from this observable.
   * 
   * @param obs
   * @return
   */
  public boolean removeObserver(Observer obs) {
    if (observers.contains(obs)) {
      observers.remove(obs);
      
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Notify all the registered observers.
   */
  public void notifyObservers(String message) {
    for (Observer obs : observers) {
      obs.update(this, message);
    }
  }
  
}
