/**
 * Programmer: Benjamin Krueper
 * Class: CS356
 * Project #: 2
 * 
 * Purpose: This is the visitor interface for visitors in this project
 * They should be able to visit Users and Groups
 */
package users;

interface Visitor {
    public void visit(User u);
    public void visit(Group g);
}
