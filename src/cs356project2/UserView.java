/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This frame simulates what a single twitter user sees. It has buttons
 * to follow other twitter users and to post a tweet.
 * It contains a visible list of the user's current subscribers and received tweets
 * When it is made, it fills the lists using information from the user, so the displayed
 * subscriptions are the same as when the user left, and the news feed contains all old
 * received tweets as well as new ones from when the user was offline
 */
package cs356project2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import users.AbstractUser;
import users.Subject;
import users.IWantToSubscribeToYouVisitor;
import users.Tweet;
import users.User;

public class UserView extends JFrame{
    
    private User user;
    
    private JPanel mainPanel;
    private JPanel userInfoPanel;
    private JPanel followPanel;
    private JPanel tweetPanel;
    private UserFollowingPanel userFollowingPanel;
    private UserNewsFeedPanel userNewsFeedPanel;
    
    private JTextField addFollowerTextField;
    private JTextField tweetTextField;
    
    private JButton followButton;
    private JButton tweetButton;
    
    private JLabel followDescription;
    private JLabel tweetDescription;
    private JLabel creationTimeLabel;
    private JLabel lastUpdateTimeLabel;
    
    
    public UserView(User u){
        this.user=u;
        
        setTitle(user.getUserName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUp();
        pack();
        setLocationRelativeTo(null);//centers the frame on the screen
        setVisible(true);
        
        loadSubscribtionsDisplay();
        loadNewsFeedDisplay();
        
        this.addWindowListener(new UserViewWindowListener());
    }
    
    //fills the subscription display with the user's existing subscriptions
    private void loadSubscribtionsDisplay(){
        List<Subject> subjects = user.getSubscriptions();
        for(Subject s: subjects){
            if(s instanceof AbstractUser){
                AbstractUser au = (AbstractUser) s;
                userFollowingPanel.addEntry(au.getUserName());
            }
            
        }
    }
    
    //fills up the news feed display with all of the tweets the user has recieved
    //the most recent is at the top
    private void loadNewsFeedDisplay(){
        List<Tweet> tweets = user.getNewsFeed();
        for(int i=tweets.size()-1;i>=0;i--){
            updateNewsFeedView(tweets.get(i));
        }
    }
    
    //adds a tweet to the top of the display
    public void updateNewsFeedView(Tweet t){
        userNewsFeedPanel.addTweetToTop(t);
        lastUpdateTimeLabel.setText("LastUpdate: " + user.getLastUpdateTime());
    }
    
    
    //subscribes the user to the user with the given username
    //if a group name is given, the user subscribes to everyone in it
    private void addSubscription(String userName){
        AbstractUser toSubscribeTo = AdminControl.getInstance().getAbstractUser(userName);
            if(toSubscribeTo==null){
                JOptionPane.showMessageDialog(null,"There is no user or group with that name.");
            }else{
                IWantToSubscribeToYouVisitor subVisiter = new IWantToSubscribeToYouVisitor(user);
                toSubscribeTo.accept(subVisiter);
                userFollowingPanel.addEntry(subVisiter.getTotalSubs());
            }
    }
    
    //makes the tweet and lets the user send it
    private void tweet(String tweetText){
        Tweet tweet = new Tweet(user.getUserName(),tweetText);
        user.tweet(tweet);
    }
    
    
    //sets up the panels manually
    private void setUp(){
        //make
        mainPanel = new JPanel();
        
        userInfoPanel = new JPanel();
        followPanel = new JPanel();
        tweetPanel = new JPanel();
        userFollowingPanel = new UserFollowingPanel();
        userNewsFeedPanel = new UserNewsFeedPanel();
        addFollowerTextField = new JTextField();
        tweetTextField = new JTextField();
        followButton = new JButton("Follow User");
        tweetButton = new JButton("Post tweet");
        followDescription = new JLabel("Follow");
        tweetDescription = new JLabel("tweet here");
        creationTimeLabel = new JLabel(user.getUserName() + " created at " + user.getCreationTime());
        lastUpdateTimeLabel = new JLabel("LastUpdate: " + user.getLastUpdateTime());
        
        //set action listeners
        addFollowerTextField.addKeyListener(new FollowTextFieldListener());
        tweetTextField.addKeyListener(new TweetTextFieldListener());
        
        FollowButtonListener followButtonListener = new FollowButtonListener();
        TweetButtonListener tweetButtonListener = new TweetButtonListener();
        followButton.addActionListener(followButtonListener);
        tweetButton.addActionListener(tweetButtonListener);
        
        addFollowerTextField.addActionListener(followButtonListener);
        tweetTextField.addActionListener(tweetButtonListener);
        
        //format
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        addFollowerTextField.setColumns(20);
        tweetTextField.setColumns(20);
        followButton.setEnabled(false);
        tweetButton.setEnabled(false);
        
        //add
        followPanel.add(followDescription);
        followPanel.add(addFollowerTextField);
        followPanel.add(followButton);
        tweetPanel.add(tweetDescription);
        tweetPanel.add(tweetTextField);
        tweetPanel.add(tweetButton);
        userInfoPanel.add(creationTimeLabel);
        userInfoPanel.add(lastUpdateTimeLabel);
        
        mainPanel.add(userInfoPanel);
        mainPanel.add(followPanel);
        mainPanel.add(userFollowingPanel);
        mainPanel.add(tweetPanel);
        mainPanel.add(userNewsFeedPanel);
        
        add(mainPanel);
    }
    
    
    //this class gives functionallity to the follow button
    private class FollowButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(addFollowerTextField.getText().equals(""))
                return;
            addSubscription(addFollowerTextField.getText());
            addFollowerTextField.setText("");
            addFollowerTextField.requestFocusInWindow();
            followButton.setEnabled(false);
        }
    }
    
    //this class gives functionallity to the tweet button
    private class TweetButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(tweetTextField.getText().equals(""))
                return;
            
            tweet(tweetTextField.getText());
            tweetTextField.setText("");
            tweetTextField.requestFocusInWindow();
            tweetButton.setEnabled(false);
        }
    }
    
    
    //this class makes sure the follow button is disabled when the follow text field
    //contains an invalid name. An Invalid name includes "", the user's username, and usernames that don't exist
    private class FollowTextFieldListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            String text = addFollowerTextField.getText();
            if(text.equals("") || text.equals(user.getUserName())){
                followButton.setEnabled(false);
            }else if(!AdminControl.getInstance().containsAbstractUser(text)){
                followButton.setEnabled(false);
            }else{
                followButton.setEnabled(true);
            }
        }
        
    }
    
    //this class disables the tweet button when the tweet text box is empty
    private class TweetTextFieldListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            String text = tweetTextField.getText();
            if(text.equals("")){
                tweetButton.setEnabled(false);
            }else{
                tweetButton.setEnabled(true);
            }
        }
        
    }
    
    //this class tells the user when there view is closed
    private class UserViewWindowListener implements WindowListener{
        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {

        }

        //tells the user their frame is closed
        @Override
        public void windowClosed(WindowEvent e) {
            user.clearView();
        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }

    
}
