/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: this panel displays the list of a user's followers
 * it is put into the User View panel and can be added to while the panel is up
 */
package cs356project2;

import javax.swing.*;
import java.awt.*;
import users.AbstractUser;

public class UserFollowingPanel extends JPanel{
    private JList list;
    private DefaultListModel listModel;
    
    //constructor
    public UserFollowingPanel(){
        setLayout(new GridLayout(1,1));

        listModel = new DefaultListModel();
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        
        //add
        add(scrollPane);

    }
    
    //adds an entry to the end of the display
    public void addEntry(String str){
        listModel.add(listModel.getSize(),str);
    }
    
    //adds multiple entries to the end of the display
    public void addEntry(java.util.List<AbstractUser> list){
        for(AbstractUser ab:list){
            addEntry(ab.getUserName());
        }
    }
    
    // method: getPreferredSize
    // purpose: returns the size of the panel
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(200,200);
    }

}
