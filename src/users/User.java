/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This class describes the twitter account of an actual person
 * It stores references to all the tweets they ever posted and all the tweets they
 * received
 */
package users;

import cs356project2.UserView;
import java.util.LinkedList;
import java.util.List;

public class User extends AbstractUser{
    private final List<Tweet> newsFeed;//sorted most recent first
    private final List<Tweet> allPosts;//sorted most recent last
    private UserView view;//this reference is to make sure only one view for this user can be up at a time
    private long lastUpdateTime;
    
    //constructor
    public User(String userName) {
        super(userName);
        newsFeed = new LinkedList<>();
        allPosts = new LinkedList<>();
        view = null;
        attach(this);//become an observer to yourself to get your tweets
        lastUpdateTime = 0;//lastUpdated time is only for the last recieved news feed. This user did not get any news yet
    }
    
    //returns the list of all tweets the user has recieved
    public List<Tweet> getNewsFeed() {
        return newsFeed;
    }
    
    //returns a list of all tweets the user has sent
    public List<Tweet> getAllPosts() {//////////////////////////////////////////////
        return allPosts;
    }
    
    //returns a reference to the user interface that is currently up
    //if the interface is closed, this returns null
    public UserView getView() {
        return view;
    }
    
    //updates the reference to the user interface
    protected void setView(UserView view) {
        this.view = view;
    }
    
    //tells the user their view is closed
    public void clearView() {
        this.view = null;
    }
    
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }
    
    //accepts a visitor
    @Override
    public void accept(Visitor v){
        v.visit(this);
    }
    
    
    //adds the tweet to the user's posts and notifies their followers of the change
    public void tweet(Tweet t){
        allPosts.add(t);
        notifyFollowers();
    }
    
    //adds the subject's latest post to the news feed
    @Override
    public void update(Subject s) {
        if(s instanceof User){
            User user = (User) s;
            List<Tweet> allUsersPosts = user.getAllPosts();
            Tweet newTweet = allUsersPosts.get(allUsersPosts.size()-1);
            recieveTweet(newTweet);
        }

    }
    
    //adds the tweet to the user's newsfeed and updates the display if it is up
    private void recieveTweet(Tweet t){
        newsFeed.add(0,t);
        lastUpdateTime = System.currentTimeMillis();
        updateViewNewsFeed(t);
    }
    
    //updated the user interface with a new tweet the user recieved
    private void updateViewNewsFeed(Tweet newTweet) {
        if(view==null)
            return;
        
        view.updateNewsFeedView(newTweet);
    }
    
    //users can't have other users organized under them
    @Override
    public boolean allowsChildren() {
        return false;
    }

    

    
    
}
