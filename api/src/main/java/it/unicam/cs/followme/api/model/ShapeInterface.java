package it.unicam.cs.followme.api.model;

/**
 * Any class that implements this interface is considered a shape.
 * @author Mohit Vijay Saini
 */
public interface ShapeInterface {

    /**
     * The label of the shape.
     * @return the label of the shape.
     */
    String getLabel();

    /**
     * Determines whether the given coordinates are inside this shape or not.
     * @param c the coordinates which may or may not be inside this shape's area.
     * @param centerShape the coordinates of center of the shape.
     * @return true if inside otherwise false.
     */
    boolean insideArea(Coordinates c, Coordinates centerShape);
}
