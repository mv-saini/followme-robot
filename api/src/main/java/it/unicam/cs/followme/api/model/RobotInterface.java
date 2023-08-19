package it.unicam.cs.followme.api.model;

/**
 * Any class that implements this interface can create a robot.
 * @param <D> Direction
 * @author Mohit Vijay Saini
 */
public interface RobotInterface<D extends Direction> {

    /**
     * Retrieves the direction of the robot.
     * @return the direction of the robot.
     */
    D getDirection();

    /**
     * Sets a new direction of the robot.
     * @param direction the new direction.
     */
    void setDirection(D direction);

    /**
     * Retrieves the label that the robot is currently signalling.
     * @return the label of the robot.
     */
    String getLabel();

    /**
     * Sets a new label for the robot to signal.
     * @param label the new label to signal.
     */
    void setLabel(String label);

    /**
     * Resets the label, the robots stops signalling.
     */
    void resetLabel();
}
