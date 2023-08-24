package it.unicam.cs.followme.api.model;

/**
 * This class represents a rectangle.
 *
 * @param width  Width of the rectangle.
 * @param height Height of the rectangle.
 * @param label  Name of the rectangle.
 * @author Mohit Vijay Saini
 * @see ShapeInterface
 */
public record ShapeRectangle(double width, double height, String label) implements ShapeInterface {

    public ShapeRectangle {
        if (width < 0 || height < 0) throw new IllegalArgumentException("Negative width or height");
    }

    @Override
    public boolean insideArea(Coordinates c, Coordinates centerShape) {
        double x_tl = centerShape.x() - (width / 2);
        double y_tl = centerShape.y() - (height / 2);
        double x_br = centerShape.x() + (width / 2);
        double y_br = centerShape.y() + (height / 2);
        return c.x() >= x_tl && c.x() <= x_br && c.y() >= y_tl && c.y() <= y_br;
    }

    /**
     * Retrieves the width of the rectangle.
     *
     * @return the width of the rectangle.
     */
    @Override
    public double width() {
        return width;
    }

    /**
     * Retrieves the height of the rectangle.
     *
     * @return the height of the rectangle.
     */
    @Override
    public double height() {
        return height;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                ", label='" + label + '\'' +
                '}';
    }
}
