/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This visitor counts the number of users (concrete users) it visits and has a method
 * getTotal() for the method that created it to extract when visiting is done
 */
package users;

public class CountUsersVisitor implements Visitor{

    private int total;
    
    //constructor
    public CountUsersVisitor(){
        total = 0;
    }
    
    //adds one to the total for each user it visits
    @Override
    public void visit(User u) {
        total++;
    }
    
    //return the total
    public int getTotal(){
        return total;
    }

    //this visitor is not interested in groups, just the visitors inside them, so this method is blank
    @Override
    public void visit(Group g) {
        
    }
    
}
