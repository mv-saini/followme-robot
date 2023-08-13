package it.unicam.cs.followme.api.model;

/**
 * This class represents a robot. It contains the direction of the robot to move towards and the label to signal.
 * @param <D> Direction
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
    public String toString() {
        return "Robot{" +
                "direction=" + direction.toString() +
                ", label='" + label + '\'' +
                '}';
    }
}
