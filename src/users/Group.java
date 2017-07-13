/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This class is a composite class that contains other Abstract Users
 * (including instances of its own class)
 */
package users;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

public class Group extends AbstractUser{
    
    //constructor
    public Group(String userName) {
        super(userName);
    }
    
    //lets the visitor visit the group, then all of the users inside
    @Override
    public void accept(Visitor v){
        v.visit(this);
        for(AbstractUser u: getMembers()){
            u.accept(v);
        }
    }
    
    //returns a list of all the group's members
    public List<AbstractUser> getMembers(){
        List<AbstractUser> list = new LinkedList<>();
        Enumeration<DefaultMutableTreeNode> e = getNode().children();
        while(e.hasMoreElements()){
            list.add((AbstractUser) e.nextElement().getUserObject());
        }
        return list;
    }

    
    //updates all of the users in this group
    @Override
    public void update(Subject s) {
        for(AbstractUser u: getMembers()){
            u.update(s);
        }
    }
    
    //the whole point of a group is to contain children, so this returns true
    @Override
    public boolean allowsChildren() {
        return true;
    }
    
}
