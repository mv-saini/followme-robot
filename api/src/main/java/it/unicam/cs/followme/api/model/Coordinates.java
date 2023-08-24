package it.unicam.cs.followme.api.model;

/**
 * This class represents a simple coordinate made up of (x,y).
 *
 * @param x The x value of the coordinate.
 * @param y The y value of the coordinate.
 * @author Mohit Vijay Saini
 */
public record Coordinates(double x, double y) {

    /**
     * The x value of the coordinate.
     *
     * @return the x value.
     */
    @Override
    public double x() {
        return x;
    }

    /**
     * The y value of the coordinate.
     *
     * @return the y value.
     */
    @Override
    public double y() {
        return y;
    }

    /**
     * Adds the given coordinate x and y values to the values of this coordinate.
     *
     * @param toAdd coordinate to add.
     * @return the added coordinate.
     */
    public Coordinates addCoordinates(Coordinates toAdd) {
        return new Coordinates(this.x + toAdd.x(),
                this.y + toAdd.y());
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
