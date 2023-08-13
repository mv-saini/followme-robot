package it.unicam.cs.followme.api.model;

import java.text.DecimalFormat;

/**
 * This class represents a direction of the robot. It contains (x,y) coordinates and the speed of the robot in m/s.
 */
public class Direction{

    /**
     * The x value of the direction.
     */
    private double x;

    /**
     * The y value of the direction.
     */
    private double y;

    /**
     * Euclidean distance between the origin (0,0) and a point (x, y)
     */
    private final double norm;

    /**
     * speed of the robot associated with the direction of the robot
     */
    private double speed;

    public Direction(double x, double y, double speed) {
        if(speed < 0) throw new IllegalArgumentException("Negative speed");
        this.norm = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        this.x = x / this.norm;
        this.y = y / this.norm;
        this.speed = speed;
    }

    /**
     * The x value of the direction.
     * @return the x value.
     */
    public double getX() {
        return x;
    }

    /**
     * The y value of the direction.
     * @return the y value.
     */
    public double getY() {
        return y;
    }

    /**
     * The speed of the robot with which it will move towards the (x,y).
     * @return The speed.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the new speed of the robot to move towards (x,y)
     * @param speed the new speed.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("####0.00");
        return "(" + df.format(this.x * this.norm) + ", " + df.format(this.y * this.norm) + ") at "
                + df.format(this.speed) + " m/s";
    }
}
