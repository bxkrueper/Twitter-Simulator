/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This visitor subscribes the passed user to any user it visits, unless
 * they are already subscribed to them. If a subscription is made, the subscribee is
 * added to a list that can be retrieved with getTotalSubs() after all visiting is done
 * The long name is the solution to confusion between subscribing to someone and having someone
 * subscribe to you
 */
package users;

import java.util.LinkedList;
import java.util.List;

public class IWantToSubscribeToYouVisitor implements Visitor{

    private final User subscriber;
    private final List<AbstractUser> totalSubs;//a list all the users that the subscriber has subscribed to because of this visitor
    
    //constructor
    public IWantToSubscribeToYouVisitor(User subscriber){
        this.subscriber = subscriber;
        totalSubs = new LinkedList<>();
    }
    
    //subscribes the subscriber to the visited user if they are not already subscribed
    @Override
    public void visit(User u) {
        if(!u.containsObserver(subscriber)){
            u.attach(subscriber);
            subscriber.addSubscription(u);
            totalSubs.add(u);
        }
    }

    //returns the list of all users the subscriber has been subscribed to by this visitor
    public List<AbstractUser> getTotalSubs() {
        return totalSubs;
    }

    //users can't subscribe to groups themselves, just the users in them, so this method is blank
    @Override
    public void visit(Group g) {
        
    }
    
}
