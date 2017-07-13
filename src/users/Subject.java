/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This interface describes all the methods subjects need for the
 * observer pattern
 */
package users;

public interface Subject {
    public void attach(Observer ob);
    public void detatch(Observer ob);
    public void notifyFollowers();
}
