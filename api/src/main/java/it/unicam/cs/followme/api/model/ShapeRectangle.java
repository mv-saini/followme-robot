package it.unicam.cs.followme.api.model;

import java.util.Objects;

/**
 * This class represents a rectangle.
 *
 * @author Mohit Vijay Saini
 * @see ShapeInterface
 */
public final class ShapeRectangle implements ShapeInterface {

    private final double width;

    private final double height;

    private final String label;


    public ShapeRectangle(double width, double height, String label) {
        if (width < 0 || height < 0) throw new IllegalArgumentException("Negative width or height");
        this.width = width;
        this.height = height;
        this.label = label;
    }

    @Override
    public boolean insideArea(Coordinates c, Coordinates centerShape) {
        double x_tl = centerShape.getX() - (width / 2);
        double y_tl = centerShape.getY() - (height / 2);
        double x_br = centerShape.getX() + (width / 2);
        double y_br = centerShape.getY() + (height / 2);
        return c.getX() >= x_tl && c.getX() <= x_br && c.getY() >= y_tl && c.getY() <= y_br;
    }

    /**
     * Retrieves the width of the rectangle.
     *
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Retrieves the height of the rectangle.
     *
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

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ShapeRectangle) obj;
        return Double.doubleToLongBits(this.width) == Double.doubleToLongBits(that.width) &&
                Double.doubleToLongBits(this.height) == Double.doubleToLongBits(that.height) &&
                Objects.equals(this.label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, label);
    }

}
