/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This class is the central control panel for running this twitter simulator.
 * it is a singleton control panel that contains the tree panel that stores the data.
 * It also contains buttons for adding users and groups, getting statistics, and opening user views.
 * 
 */
package cs356project2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import users.AbstractUser;
import users.CountGroupsVisitor;
import users.CountMessagesVisitor;
import users.CountUsersVisitor;
import users.FindLastUpdatedUserVisitor;
import users.Group;
import users.OpenUserViewVisitor;
import users.PositivePercentageVisitor;
import users.User;
import users.ValidVisitor;

public class AdminControl extends JFrame{

    private static AdminControl instance;
    
    private JPanel mainPanel;
    
    private JPanel leftPanel;
    private JPanel rightPanel;
    
    private TreeViewPanel treeViewPanel;
    private JLabel treeDescriptionLabel;
    
    private JPanel addPanel;
    private JPanel openPanel;
    private StatPanel statPanel;
    
    
    private JPanel addUserPanel;
    private JPanel addGroupPanel;
    
    private JButton openUserViewButton;
    
    private JTextField addUserTextField;
    private JButton addUserButton;
    private JTextField addGroupTextField;
    private JButton addGroupButton;
    
    
    //constructor
    private AdminControl(){
        setTitle("Admin Control");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setUp();
        
        getContentPane().setPreferredSize‌​(new Dimension(600, 400));
        pack();
        setLocationRelativeTo(null);//centers the frame on the screen
        setVisible(true);
    }
    
    
    //input: the abstract user that is currently selected in the tree
    //purpose: disables the open user button if no user is selected (and enables it again if something is selected)
    protected void selectionChanged(AbstractUser u){
        if(u==null){
            openUserViewButton.setEnabled(false);
        }else{
            openUserViewButton.setEnabled(true);
        }
    }
    
    //gets the user with the given username
    public AbstractUser getAbstractUser(String str){
        return treeViewPanel.getAbstractUser(str);
    }
    
    //returns whether or not a user with this username is in the tree
    public boolean containsAbstractUser(String str){
        return treeViewPanel.containsAbstractUser(str);
    }
    
    
    //returns the singleton instance
    public static AdminControl getInstance() {
        if(instance==null)
            instance = new AdminControl();
        return instance;
    }
    
    //sends a visitor to the root node to obtain the total number of users
    public int getNumberOfUsers(){
        CountUsersVisitor visitor = new CountUsersVisitor();
        treeViewPanel.getRootGroup().accept(visitor);
        return visitor.getTotal();
    }
    
    //sends a visitor to the root node to obtain the total number of groups
    //it doesn't count root the root as a group
    public int getNumberOfGroups(){
        CountGroupsVisitor visitor = new CountGroupsVisitor();
        treeViewPanel.getRootGroup().accept(visitor);
        return visitor.getTotal()-1;//-1 to not count root
    }
    
    //sends a visitor to the root node to obtain the number tweets that have ever been posted
    public int getNumberOfMessages(){
        CountMessagesVisitor visitor = new CountMessagesVisitor();
        treeViewPanel.getRootGroup().accept(visitor);
        return visitor.getTotal();
    }
    
    //sends a visitor to the root node to obtain the percentage of positive tweets that have ever been posted
    public double getPositivePercentage(){
        PositivePercentageVisitor visitor = new PositivePercentageVisitor();
        treeViewPanel.getRootGroup().accept(visitor);
        return visitor.getPercentage();
    }
    
    public boolean checkNameValidity(){
        ValidVisitor visitor = new ValidVisitor();
        treeViewPanel.getRootGroup().accept(visitor);
        return visitor.getResult();
    }
    
    public AbstractUser getLastUpdatedUser(){
        FindLastUpdatedUserVisitor visitor = new FindLastUpdatedUserVisitor();
        treeViewPanel.getRootGroup().accept(visitor);
        return visitor.getLatestUser();
    }
    
    

    //this class attempts to add a user with the name in the user id text field when the button is clicked
    //if it can't add it, a message is displayed
    private class AddUserListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = addUserTextField.getText();
            if(text.equals("")){
                JOptionPane.showMessageDialog(null,"Invalid name");
                return;
            }
            boolean sucsess = treeViewPanel.addAbstractUser(new User(text));
            if(sucsess){
                addUserButton.setEnabled(false);
                if(addGroupTextField.getText().equals(text)){
                    addGroupButton.setEnabled(false);
                }
            }else{
                JOptionPane.showMessageDialog(null,"User could not be added: Username already exists!");
            }
            addUserTextField.requestFocusInWindow();
        }
        
    }
    
    //this class attempts to add a group with the name in the group id text field when the button is clicked
    //if it can't add it, a message is displayed
    private class AddGroupListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = addGroupTextField.getText();
            if(text.equals("")){
                JOptionPane.showMessageDialog(null,"Invalid name");
                return;
            }
            boolean sucsess = treeViewPanel.addAbstractUser(new Group(text));
            if(sucsess){
                addGroupButton.setEnabled(false);
                if(addUserTextField.getText().equals(text)){
                    addUserButton.setEnabled(false);
                }
            }else{
                JOptionPane.showMessageDialog(null,"User could not be added: Username already exists!");
            }
            addGroupTextField.requestFocusInWindow();
        }
        
    }
    
    //whenever the text in the user field is changed, this class enables or
    //disables the corresponding add button depending on if it is possible to add that name
    private class UserTextFieldListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            //this method is not needed
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //this method is not needed
        }

        @Override
        public void keyReleased(KeyEvent e) {
            String text = addUserTextField.getText();
            if(text.equals("")){
                addUserButton.setEnabled(false);
            }else{
                addUserButton.setEnabled(true);
            }
        }
        
    }
    
    //whenever the text in the group field is changed, this class enables or
    //disables the corresponding add button depending on if it is possible to add that name
    private class GroupTextFieldListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            //this method is not needed
        }

        @Override
        public void keyPressed(KeyEvent e) {
            //this method is not needed
        }

        @Override
        public void keyReleased(KeyEvent e) {
            String text = addGroupTextField.getText();
            if(text.equals("")){
                addGroupButton.setEnabled(false);
            }else{
                addGroupButton.setEnabled(true);
            }
        }
        
    }
    
    //for the open user view button
    //using a visitor allows all users under a selected group to be opened at once if a group is selected
    private class UserViewListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            OpenUserViewVisitor visitor = new OpenUserViewVisitor();
            treeViewPanel.getSelectedUser().accept(visitor);
        }
        
    }
    
    
    
    
    
    
    
    //sets up the components of the panel manually
    private void setUp(){
        //make
        addUserTextField = new JTextField();
        addUserButton = new JButton("Add User");
        addGroupTextField = new JTextField();
        addGroupButton = new JButton("Add Group");
        
        openUserViewButton = new JButton("Open user view");

        
        addUserPanel = new JPanel();
        addGroupPanel = new JPanel();
        
        addPanel = new JPanel();
        openPanel = new JPanel();
        
        statPanel = new StatPanel();
        
        treeDescriptionLabel = new JLabel("Tree");
        treeViewPanel = new TreeViewPanel();
        
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        
        mainPanel = new JPanel();
        
        //format
        addUserTextField.setColumns(20);
        addGroupTextField.setColumns(20);
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(200,700));
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(350,700));
        mainPanel.setLayout(new BorderLayout());
        addUserButton.setEnabled(false);
        addGroupButton.setEnabled(false);
        openUserViewButton.setEnabled(false);
        
        //add listeners
        
        addUserTextField.addKeyListener(new UserTextFieldListener());
        addGroupTextField.addKeyListener(new GroupTextFieldListener());
        
        AddUserListener addUserListener = new AddUserListener();
        AddGroupListener addGroupListener = new AddGroupListener();
        UserViewListener userViewListener = new UserViewListener();
        addUserButton.addActionListener(addUserListener);
        addUserTextField.addActionListener(addUserListener);//lets hitting enter here do the same thing as the button
        addGroupButton.addActionListener(addGroupListener);
        addGroupTextField.addActionListener(addGroupListener);//lets hitting enter here do the same thing as the button
        openUserViewButton.addActionListener(userViewListener);
        
        
        //add
        addUserPanel.add(addUserTextField);
        addUserPanel.add(addUserButton);
        addGroupPanel.add(addGroupTextField);
        addGroupPanel.add(addGroupButton);
        
        addPanel.add(addUserPanel);
        addPanel.add(addGroupPanel);
        
        openPanel.add(openUserViewButton);
        
        
        rightPanel.add(addPanel);
        rightPanel.add(openPanel);
        rightPanel.add(statPanel);
        
        leftPanel.add(treeDescriptionLabel);
        leftPanel.add(treeViewPanel);
        
        mainPanel.add(leftPanel,BorderLayout.WEST);
        mainPanel.add(rightPanel,BorderLayout.EAST);
        
        add(mainPanel);
    }
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(700,500);
    }
    
}
