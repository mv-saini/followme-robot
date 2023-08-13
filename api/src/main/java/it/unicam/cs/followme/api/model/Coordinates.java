package it.unicam.cs.followme.api.model;

/**
 * This class represents a simple coordinate made up of (x,y)
 */
public class Coordinates {

    /**
     * The x value of the coordinate.
     */
    private final double x;

    /**
     * The y value of the coordinate.
     */
    private final double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The x value of the coordinate.
     * @return the x value.
     */
    public double getX() {
        return x;
    }

    /**
     * The y value of the coordinate.
     * @return the y value.
     */
    public double getY() {
        return y;
    }

    /**
     * Adds the given coordinate x and y values to the values of this coordinate.
     * @param toAdd coordinate to add.
     * @return the added coordinate.
     */
    public Coordinates addCoordinates(Coordinates toAdd){
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
}
