/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This visitor counts the number of groups it visits and has a method
 * getTotal() for the method that created it to extract when visiting is done
 */
package users;

public class CountGroupsVisitor implements Visitor{

    private int total;
    
    //constructor
    public CountGroupsVisitor(){
        total = 0;
    }
    
    //this visitor is not interested in groups, so this method is left blank
    @Override
    public void visit(User u) {
        
    }
    
    //adds one to the total for each group it visits
    @Override
    public void visit(Group g) {
        total++;
    }
    
    //return the total
    public int getTotal(){
        return total;
    }
}
