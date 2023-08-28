package it.unicam.cs.followme.api.model;

import java.util.Objects;

/**
 * This class represents a circle.
 *
 * @author Mohit Vijay Saini
 * @see ShapeInterface
 */
public final class ShapeCircle implements ShapeInterface {

    private final double radius;

    private final String label;


    public ShapeCircle(double radius, String label) {
        if (radius < 0) throw new IllegalArgumentException("Negative radius");
        this.radius = radius;
        this.label = label;
    }

    @Override
    public boolean insideArea(Coordinates c, Coordinates centerShape) {
        //SQRT((x - h)² + (y - k)²) ≤ r
        double distance = Math.sqrt(Math.pow(c.getX() - centerShape.getX(), 2) + Math.pow(c.getY() - centerShape.getY(), 2));
        return distance <= radius;
    }

    /**
     * Retrieves the radius of the circle.
     *
     * @return the radius of the circle.
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
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
        var that = (ShapeCircle) obj;
        return Double.doubleToLongBits(this.radius) == Double.doubleToLongBits(that.radius) &&
                Objects.equals(this.label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius, label);
    }

}
