/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This class describes a general Twitter user
 * They are both Subjects and Observers
 */
package users;

import java.util.LinkedList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

public abstract class AbstractUser implements Subject, Observer{

    private final String userName;
    private final List<Subject> subscriptions;
    private final List<Observer> observers;
    private DefaultMutableTreeNode node;//the node that the user belongs to in the tree
    private final long creationTime;
    
    //constructor
    public AbstractUser(String userName){
        super();
        this.userName = userName;
        subscriptions = new LinkedList<>();
        observers = new LinkedList<>();
        node = null;
        creationTime = System.currentTimeMillis();
    }
    
    //sets the reference to this user's node. this is not intended to change, but trying to
    //do it in the constructor proved awkward, as the node was not created yet
    public void setNode(DefaultMutableTreeNode n){
        if(node==null)
            node = n;
    }
    
    //returns the node in the JTree this user belongs to
    protected DefaultMutableTreeNode getNode(){
        return node;
    }
    
    //gets the username
    public String getUserName() {
        return userName;
    }
    
    /**
     * @return the creationTime
     */
    public final long getCreationTime() {
        return creationTime;
    }
    
    //returns the list of all the subjects the user is subscribed to
    public List<Subject> getSubscriptions() {
        return subscriptions;
    }
    
    //adds the subject to the subscriber list
    public void addSubscription(Subject s){
        getSubscriptions().add(s);
    }
    
    //adds the observer to the observer list, unless that observer is already there
    public void attach(Observer ob){
        if(!observers.contains(ob)){
            getObservers().add(ob);
        }
    }
    
    //removes the observer from the observer list
    public void detatch(Observer ob){
        getObservers().remove(ob);
    }
    
    //lets all of this user's subscribers this user's state has changed
    //ex: a tweet has been sent
    @Override
    public void notifyFollowers() {
        for(Observer ob: getObservers()){
            ob.update(this);
        }
    }
    
    //returns whether or not the observer is subscribed to this user
    public boolean containsObserver(Observer ob){
        return observers.contains(ob);
    }

    //returns the list of all observers
    public List<Observer> getObservers() {
        return observers;
    }
    
    public abstract boolean allowsChildren();
    public abstract void accept(Visitor v);
    
    //this just returns the username for the JTree display
    public String toString(){
        return getUserName();
    }
    
    //usernames are unique, so this function just checks if the object is the same
    //type and has the same username
    @Override
    public boolean equals(Object o){
        if(o instanceof AbstractUser){
            AbstractUser ab = (AbstractUser) o;
            return userName.equals(ab.userName);
        }
        return false;
    }
    
    //this lets instances of this class be used in a hash table. Usernames are unique, so
    //the hashing function just uses that
    @Override
    public int hashCode(){
        return userName.hashCode();
    }

    
    
}
