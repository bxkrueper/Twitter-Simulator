/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This visitor checks if all the usernames are different and do not contain
 * spaces. if this is not the case, it returns false as the result.
 */
package users;

import java.util.HashSet;
import java.util.Set;

public class ValidVisitor implements Visitor{

    private boolean everythingIsOkay;
    private Set<String> usernames;
    
    //constructor
    public ValidVisitor(){
        everythingIsOkay = true;
        usernames = new HashSet<>();
    }
    
    @Override
    public void visit(User u) {
        visitAbstractUser(u);
    }

    @Override
    public void visit(Group g) {
        visitAbstractUser(g);
    }
    
    //this visitor treats users and groups the same
    public void visitAbstractUser(AbstractUser u){
        if(!everythingIsOkay){
            return;//at least one wrong name was found, so nothing else will change the result
        }
        
        String name = u.getUserName();
        if(usernames.contains(name) || name.contains(" ")){
            everythingIsOkay = false;
        }else{
            usernames.add(name);
        }
    }
    
    public boolean getResult(){
        return everythingIsOkay;
    }
    
}
