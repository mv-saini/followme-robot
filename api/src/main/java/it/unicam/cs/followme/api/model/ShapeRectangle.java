package it.unicam.cs.followme.api.model;

/**
 * This class represents a rectangle.
 */
public class ShapeRectangle implements ShapeInterface {

    /**
     * Width of the rectangle.
     */
    private final double width;

    /**
     * Height of the rectangle.
     */
    private final double height;

    /**
     * Name of the rectangle.
     */
    private final String label;

    public ShapeRectangle(double width, double height, String label) {
        if (width < 0 || height < 0) throw new IllegalArgumentException("Negative width or height");
        this.width = width;
        this.height = height;
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean insideArea(Coordinates c, Coordinates centerShape) {
        double x_tl = centerShape.getX() - (width / 2);
        double y_tl = centerShape.getY() - (height / 2);
        double x_br = centerShape.getX()  + (width / 2);
        double y_br = centerShape.getY() + (height / 2);
        return c.getX()  >= x_tl && c.getX()  <= x_br && c.getY() >= y_tl && c.getY() <= y_br;
    }

    /**
     * Retrieves the width of the rectangle.
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Retrieves the height of the rectangle.
     * @return the height of the rectangle.
     */
    public double getHeight() {
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
