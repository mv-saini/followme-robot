package it.unicam.cs.followme.api.utility;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.api.parsing.loops.LoopProgramsInterface;
import it.unicam.cs.followme.utilities.RobotCommand;

/**
 * This class represents the linked list of programs.
 * @see Program
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 * @author Mohit Vijay Saini
 */
public class LinkedPrograms<R extends RobotInterface<Direction>, S extends ShapeInterface> {

    /**
     * Inner node class that contains the program counter of the program, the program itself and points to the next
     * node.
     * @param <R> Robots that extends {@link RobotInterface}
     * @param <S> Shapes that extends {@link ShapeInterface}
     */
    private static class Node<R extends RobotInterface<Direction>, S extends ShapeInterface> {

        /**
         * Program number.
         */
        int programCounter;

        /**
         * The program.
         */
        final Program<R, S> data;

        /**
         * Next node containing the program.
         */
        Node<R, S> next;

        /**
         * Generates a node.
         * @param data the program.
         * @param counter program number.
         * @param next next node.
         */
        Node(Program<R, S> data, int counter, Node<R, S> next) {
            this.data = data;
            this.programCounter = counter;
            this.next = next;
        }
    }

    /**
     * The program counter that increments by 1 when a node is connected to the list.
     */
    private int counter;

    /**
     * First node of the list.
     */
    private Node<R, S> head;

    /**
     * Last node of the list.
     */
    private Node<R, S> tail;

    /**
     * Generates the new node with the given program and connect the new node to the end of the list.
     * @param element element to be added
     */
    public void add(Program<R, S> element) {
        Node<R, S> newNode  = new Node<>(element, this.counter, null);
        if(this.head == null){
            this.head = newNode;
        }
        else{
            this.tail.next = newNode;
        }
        this.tail = newNode;
        this.counter++;
    }

    /**
     * Calculates and returns the total number of nodes in the list.
     * @return the size of the list.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    public int size() {
        int size = 0;

        for (Node<R, S> it = this.head; it != null; ++size, it = it.next) {}

        return size;
    }

    /**
     * Finds the node with the given index (program counter).
     * @param index the number of the node.
     * @return the node with the given number.
     */
    private Node<R, S> getNode(int index) {
        if(size() < 1)
            return null;

        Node<R, S> it = this.head;
        while(it.next != null){
            if(it.programCounter == index){
                return it;
            }
            it = it.next;
        }
        return it;
    }

    /**
     * Retrieves the name of the program.
     * @param index the program counter of the program
     * @return the name of the program.
     */
    public RobotCommand getNodeRobotCommand(int index){
        Node<R, S> it = getNode(index);
        if(it == null)
            return null;
        return it.data.getProgramLabel();
    }

    /**
     * Retrieves the arguments of the program.
     * @param index the program counter of the program
     * @return the arguments of the program.
     */
    public String[] getNodeArgs(int index){
        Node<R, S> it = getNode(index);
        if(it == null)
            return null;
        return it.data.getArgs();
    }

    /**
     * Retrieves the loop type of the program.
     * @param index the program counter of the program
     * @return the loop type of the program.
     */
    public LoopProgramsInterface<R, S> getNodeLoopType(int index){
        Node<R, S> it = getNode(index);
        if(it == null)
            return null;
        return it.data.getLoopType();
    }


    @Override
    public String toString() {
        String s = "";
        Node<R, S> temp = this.head;
        while(temp.next != null){
            s = s.concat(temp.programCounter + " " + temp.data + "\n");
            temp = temp.next;
        }
        s = s.concat(temp.programCounter + " " + temp.data + "\n");
        return s;
    }
}
