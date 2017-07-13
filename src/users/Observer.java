/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This interface says that all observers must have a method that lets them
 * retrieve the information they need from a subject
 */
package users;

public interface Observer {
    public void update(Subject s);
}
