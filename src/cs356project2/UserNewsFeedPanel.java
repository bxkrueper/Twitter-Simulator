/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: this panel shows the user's news feed with the most recent post at the top
 * It is put into the User View panel and can be updated while the panel is up
 */
package cs356project2;

import javax.swing.*;
import java.awt.*;
import users.Tweet;

public class UserNewsFeedPanel extends JPanel{
    private JList list;
    private DefaultListModel listModel;
    
    //constructor
    public UserNewsFeedPanel(){
        setLayout(new GridLayout(1,1));
        
        listModel = new DefaultListModel();
        list = new JList(listModel);
        JScrollPane scrollPane = new JScrollPane(list);

        //add
        add(scrollPane);
        
        
    }
    
    //puts the tweet at the top of the display
    public void addTweetToTop(Tweet t){
        listModel.add(0,t.getDisplayText());
    }
    
    // method: getPreferredSize
    // purpose: returns the size of the panel
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(500,400);
    }

}
