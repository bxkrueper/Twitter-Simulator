/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This visitor adds up all the messages that each user has posted and has a method
 * getTotal() for the method that created it to extract when visiting is done
 */
package users;

public class CountMessagesVisitor implements Visitor{

    private int total;
    
    //constructor
    public CountMessagesVisitor(){
        total = 0;
    }
    
    //adds the user's post count to the total
    @Override
    public void visit(User u) {
        total += u.getAllPosts().size();
    }
    
    //returns the total
    public int getTotal(){
        return total;
    }

    //groups don't post messages themselves, so this visitor ignores them
    @Override
    public void visit(Group g) {
        
    }
    
}
