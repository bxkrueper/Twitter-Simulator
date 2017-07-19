/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This visitor keeps a reference to the user with the latest time
 * that it has visited. This can be returned when the visitor is done
 */
package users;

public class FindLastUpdatedUserVisitor implements Visitor{

    private AbstractUser latestUser;
    private long latestTime;
    
    //constructor
    public FindLastUpdatedUserVisitor(){
        latestUser= null;
        latestTime=-1;
    }
    
    //if the new user's update time is greater than the current one's time,
    //update the fields
    @Override
    public void visit(User u) {
        if(u.getLastUpdateTime()>latestTime){
            latestUser = u;
            latestTime = u.getLastUpdateTime();
        }
    }
    
    //returns the latest user
    public AbstractUser getLatestUser(){
        return latestUser;
    }

    //this visitor is not interested in groups
    @Override
    public void visit(Group g) {
        
    }
    
}
