/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: this visitor adds up the total number of posts and how many of them
 * were positive. It has a method getPercentage() to return the percentage of
 * positive posts when it is done visiting
 */
package users;

import java.util.List;

public class PositivePercentageVisitor implements Visitor{

    private int totalMessages;
    private int positiveTotal;
    
    //constructor
    public PositivePercentageVisitor(){
        totalMessages = 0;
        positiveTotal = 0;
    }
    
    //adds the total number of tweets from the user to totalMessages
    //and how many of those tweets are positive to positive total
    @Override
    public void visit(User u) {
        List<Tweet> tweetList = u.getAllPosts();
        totalMessages += tweetList.size();
        for(Tweet t:tweetList){
            if(t.isPositive())
                positiveTotal++;
        }
    }
    
    //returns the percentage of posts that are positive
    public double getPercentage(){
        return positiveTotal/(double) totalMessages;
    }

    //groups can't post messages themselves, so this method is left blank
    @Override
    public void visit(Group g) {
        
    }
    
}
