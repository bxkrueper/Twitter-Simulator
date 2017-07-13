/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This visitor opens the user view for each user it visits
 * If that user view is already open, it makes the view frame request focus instead
 */
package users;

import cs356project2.UserView;

public class OpenUserViewVisitor implements Visitor{

    //opens the view of the user it visits
    //if their view is already open, it brings the view to attention by requesting it be focused on
    @Override
    public void visit(User u) {
        if(u.getView()==null){
            u.setView(new UserView(u));
        }else{
            u.getView().requestFocus();
        }
        
    }

    //groups can't have interfaces, so this method is blank
    @Override
    public void visit(Group g) {
        
    }
    
}
