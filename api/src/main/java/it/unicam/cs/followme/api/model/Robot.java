package it.unicam.cs.followme.api.model;

import java.util.Objects;

/**
 * This class represents a robot. It contains the direction of the robot to move towards and the label to signal.
 * @param <D> Direction
 * @author Mohit Vijay Saini
 * @see RobotInterface
 */
public class Robot<D extends Direction> implements RobotInterface<D>{

    private D direction;

    private String label;

    public Robot(D direction) {
        if(direction == null) throw new IllegalArgumentException("Direction is null");
        this.direction = direction;
        this.label = "";
    }

    @Override
    public D getDirection() {
        return direction;
    }

    @Override
    public void setDirection(D direction) {
        this.direction = direction;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void resetLabel(){
        this.label = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Robot<?> robot = (Robot<?>) o;
        return Objects.equals(direction, robot.direction) && Objects.equals(label, robot.label);
    }

    @Override
    public String toString() {
        return "Robot{" +
                "direction=" + direction.toString() +
                ", label='" + label + '\'' +
                '}';
    }
}
