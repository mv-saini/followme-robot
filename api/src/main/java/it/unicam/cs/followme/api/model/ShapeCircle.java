package it.unicam.cs.followme.api.model;

/**
 * This class represents a circle.
 */
public class ShapeCircle implements ShapeInterface {

    /**
     * Radius of the circle.
     */
    private final double radius;

    /**
     * Name of the circle.
     */
    private final String label;


    public ShapeCircle(double radius, String label) {
        if (radius < 0) throw new IllegalArgumentException("Negative radius");
        this.radius = radius;
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean insideArea(Coordinates c, Coordinates centerShape) {
        //(x - h)² + (y - k)² ≤ r²
        double distance = Math.sqrt(Math.pow(c.getX() - centerShape.getX(), 2) + Math.pow(c.getY() - centerShape.getY(), 2));
        return distance <= radius;
    }

    /**
     * Retrieves the radius of the circle.
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
}
