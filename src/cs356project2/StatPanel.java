/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This class is put inside the Admin Control panel.
 * It has buttons to show informations about the current state of the twitter
 * simulator. It displays the information by updating the text of a label.
 * It gets the information by accessing the Admin Control singleton
 */
package cs356project2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatPanel extends JPanel{
    private JPanel buttonPanel;
    private JPanel displayPanel;
    
    private JLabel displayInfoLabel;
    
    private JButton showUserTotalButton;
    private JButton showGroupTotalButton;
    private JButton showMessagesTotalButton;
    private JButton showPositivePercentageButton;
    private JPanel showUserTotalButtonPanel;
    private JPanel showGroupTotalButtonPanel;
    private JPanel showMessagesTotalButtonPanel;
    private JPanel showPositivePercentageButtonPanel;
    
    //constructor
    public StatPanel(){
        //make components
        displayInfoLabel = new JLabel("Info");
        displayPanel = new JPanel();
        
        buttonPanel = new JPanel();
        showUserTotalButton = new JButton("Show total users");
        showGroupTotalButton = new JButton("Show total groups");
        showMessagesTotalButton = new JButton("Show total messages");
        showPositivePercentageButton = new JButton("Show % positive");
        showUserTotalButtonPanel = new JPanel();
        showGroupTotalButtonPanel = new JPanel();
        showMessagesTotalButtonPanel = new JPanel();
        showPositivePercentageButtonPanel = new JPanel();
        
        
        //format components
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        buttonPanel.setLayout(new GridLayout(2,2));
        
        //add action listeners
        showUserTotalButton.addActionListener(new ShowUserTolalListener());
        showGroupTotalButton.addActionListener(new ShowGroupTolalListener());
        showMessagesTotalButton.addActionListener(new ShowMessageTolalListener());
        showPositivePercentageButton.addActionListener(new ShowPositivePercentageListener());
        
        //add components
        displayPanel.add(displayInfoLabel);
        showUserTotalButtonPanel.add(showUserTotalButton);
        showGroupTotalButtonPanel.add(showGroupTotalButton);
        showMessagesTotalButtonPanel.add(showMessagesTotalButton);
        showPositivePercentageButtonPanel.add(showPositivePercentageButton);
        buttonPanel.add(showUserTotalButtonPanel);
        buttonPanel.add(showGroupTotalButtonPanel);
        buttonPanel.add(showMessagesTotalButtonPanel);
        buttonPanel.add(showPositivePercentageButtonPanel);
        
        add(displayPanel);
        add(buttonPanel);
    }
    
    //this class gives functionality to the show user total button: updates the display label with the infomation
    private class ShowUserTolalListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            displayInfoLabel.setText("Total Users: " + Integer.toString(AdminControl.getInstance().getNumberOfUsers()));
        }
        
    }
    
    //this class gives functionality to the show group total button: updates the display label with the infomation
    private class ShowGroupTolalListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            displayInfoLabel.setText("Total Groups: " + Integer.toString(AdminControl.getInstance().getNumberOfGroups()));
        }
        
    }
    
    //this class gives functionality to the show message total button: updates the display label with the infomation
    private class ShowMessageTolalListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            displayInfoLabel.setText("Total Messages Sent: " + Integer.toString(AdminControl.getInstance().getNumberOfMessages()));
        }
        
    }
    
    //this class gives functionality to the show Positive Percentage button: updates the display label with the infomation
    private class ShowPositivePercentageListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            double percentage = AdminControl.getInstance().getPositivePercentage();
            if(Double.isNaN(percentage))
                displayInfoLabel.setText("Positive percentage: N/A");
            else{
                String str = String.format("%.1f", percentage*100);
                displayInfoLabel.setText("Positive percentage: " + str + "%");
            }
        }
        
    }
}
