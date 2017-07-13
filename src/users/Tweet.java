/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This class describes a tweet. It is composed of the name of the poster
 * and the the actual message.
 */
package users;

import java.util.regex.Pattern;

public class Tweet {
    
    private String userName;
    private String message;
    private long timeCreated;
    
    //constructor
    public Tweet(String un,String m){
        this.userName=un;
        this.message = m;
        this.timeCreated = System.nanoTime();
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    
    //returns the time the tweet was created
    public long getTimeCreated(){
        return timeCreated;
    }
    
    //returns how the tweet should appear in the message board
    public String getDisplayText(){
        return userName + ": " + message;
    }
    
    //returns whether certain positive words are contained in the message
    public boolean isPositive(){
        if(messageContainsCaseInsensitive("good"))
            return true;
        if(messageContainsCaseInsensitive("great"))
            return true;
        if(messageContainsCaseInsensitive("awesome"))
            return true;
        if(messageContainsCaseInsensitive("quality"))
            return true;
        if(messageContainsCaseInsensitive("super"))
            return true;
        if(messageContainsCaseInsensitive("excellent"))
            return true;
        if(messageContainsCaseInsensitive("marvelous"))
            return true;
        if(messageContainsCaseInsensitive("superb"))
            return true;
        if(messageContainsCaseInsensitive("wonderful"))
            return true;
        if(messageContainsCaseInsensitive("nice"))
            return true;
        if(messageContainsCaseInsensitive("splendid"))
            return true;
        if(messageContainsCaseInsensitive("stupendous"))
            return true;
        if(messageContainsCaseInsensitive("neat"))
            return true;
        if(messageContainsCaseInsensitive("admire"))
            return true;
        if(messageContainsCaseInsensitive("=)"))
            return true;
        if(messageContainsCaseInsensitive(":)"))
            return true;
        if(messageContainsCaseInsensitive("(="))
            return true;
        if(messageContainsCaseInsensitive("(:"))
            return true;
        return false;
    }
    
    private boolean messageContainsCaseInsensitive(String small){
        return Pattern.compile(Pattern.quote(small), Pattern.CASE_INSENSITIVE).matcher(message).find();
    }
    
    //returns information about all the fields
    @Override
    public String toString(){
        return "Poster: " + userName + "message: " + message + " time sent: " + timeCreated;
    }
}
