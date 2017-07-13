/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This class is a panel that uses a JTree to display add and store the users in this
 * twitter simulation
 * It contains a listener that notifies Admin Control of when a node is selected and what it is
 * 
 * thanks to https://docs.oracle.com/javase/tutorial/uiswing/examples/components/DynamicTreeDemoProject/src/components/DynamicTree.java
 * for providing an example of how to work with JTrees
 */

package cs356project2;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import users.AbstractUser;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import users.Group;

public class TreeViewPanel extends JPanel{
    
    private final DefaultMutableTreeNode rootNode;
    private final DefaultTreeModel treeModel;
    private final JTree tree;
    private final Map<String,DefaultMutableTreeNode> stringToNodeMap;//this map provides a constant time way to look up users by username

    //constructor
    public TreeViewPanel(){
        setLayout(new GridLayout(1,1));//makes it fill the available space
        
        stringToNodeMap = new HashMap<>();

        Group rootGroup = new Group("Root");// a group called "Root" is used as the root of the tree. Everything else will be a child of it
        rootNode = new DefaultMutableTreeNode(rootGroup);
        rootGroup.setNode(rootNode);
        stringToNodeMap.put(rootGroup.getUserName(), rootNode);
        
        treeModel = new DefaultTreeModel(rootNode);
        tree = new JTree(treeModel);
        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        tree.addTreeSelectionListener(new MyTreeSelectionListener());
        
        JScrollPane treeScrollPane = new JScrollPane(tree);
        add(treeScrollPane);
    }
    
    //this is a constant time method to check if a user name is taken
    private boolean containsAbstractUser(AbstractUser u){/////////////////////////////////////////////map?
        return stringToNodeMap.containsKey(u.getUserName());
    }
    //this is a constant time method to check if a user name is taken
    public boolean containsAbstractUser(String str){
        return stringToNodeMap.containsKey(str);
    }
    
    //adds the user to the tree
    //returns whether or not the addition was sucsessful
    public boolean addAbstractUser(AbstractUser u){
        if(containsAbstractUser(u)){
            return false;
        }
        
        //makes the node for the user and gives it a refrence to the node
	DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(u);
        u.setNode(childNode);
        
        
        stringToNodeMap.put(u.getUserName(), childNode);//put the node in the map to easily search for it
        
        childNode.setAllowsChildren(u.allowsChildren());
        
        //adds the node
        DefaultMutableTreeNode parentNode = getParentForAdding();
        treeModel.insertNodeInto(childNode, parentNode,parentNode.getChildCount());
        
        
        tree.scrollPathToVisible(new TreePath(childNode.getPath()));//makes the new addition visable
        
        return true;
    }
    
    //gets the parent that a new node will have.
    //if a group is selected, that will be the parent
    //if a user is selected, its parent will be returned and the new node will be a sibling
    //if no nodes are selected, the root node will be the parent
    private DefaultMutableTreeNode getParentForAdding(){
        DefaultMutableTreeNode selectedNode = getSelectedNode();
        if(selectedNode==null)
            return rootNode;
        else{
            if(selectedNode.getAllowsChildren()){
                return selectedNode;
            }else{
                return (DefaultMutableTreeNode) selectedNode.getParent();
            }
        }
    }
    
    //returns the selected user
    public AbstractUser getSelectedUser(){
        DefaultMutableTreeNode selectedNode = getSelectedNode();
        if(selectedNode==null)
            return null;
        else{
            return (AbstractUser) selectedNode.getUserObject();
        }
        
    }
    
    //returns the root user for visiors to start off at
    public AbstractUser getRootGroup(){
        return (AbstractUser) rootNode.getUserObject();
    }
    
    //quickly returns the node with the user that has the given user name
    //returns null if it can't find it
    private DefaultMutableTreeNode getNode(String str){
        return stringToNodeMap.get(str);
    }
    
    //quickly returns the abstract user  that has the given user name
    //returns null if it can't find it
    public AbstractUser getAbstractUser(String str){
        DefaultMutableTreeNode node = getNode(str);
        if(node==null)
            return null;
        else 
            return (AbstractUser) node.getUserObject();
    }
    
    //returns the node that is selected
    private DefaultMutableTreeNode getSelectedNode(){
        TreePath selectedPath = tree.getSelectionPath();
        if (selectedPath == null)
            return null;
        else{
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)(selectedPath.getLastPathComponent());
            return selectedNode;
        }
    }
    
    //this class keeps admin control informed on which node is selected
    private class MyTreeSelectionListener implements TreeSelectionListener{

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            AdminControl.getInstance().selectionChanged(getSelectedUser());
        }
        
    }
    
}