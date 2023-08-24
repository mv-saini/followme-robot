package it.unicam.cs.followme.api.model;

import java.util.Objects;

/**
 * This class represents a simple coordinate made up of (x,y).
 *
 * @author Mohit Vijay Saini
 */
public final class Coordinates {

    private final double x;

    private final double y;

    /**
     * @param x The x value of the coordinate.
     * @param y The y value of the coordinate.
     */
    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The x value of the coordinate.
     *
     * @return the x value.
     */
    public double getX() {
        return x;
    }

    /**
     * The y value of the coordinate.
     *
     * @return the y value.
     */
    public double getY() {
        return y;
    }

    /**
     * Adds the given coordinate x and y values to the values of this coordinate.
     *
     * @param toAdd coordinate to add.
     * @return the added coordinate.
     */
    public Coordinates addCoordinates(Coordinates toAdd) {
        return new Coordinates(this.x + toAdd.getX(),
                this.y + toAdd.getY());
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Coordinates) obj;
        return Double.doubleToLongBits(this.x) == Double.doubleToLongBits(that.x) &&
                Double.doubleToLongBits(this.y) == Double.doubleToLongBits(that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
