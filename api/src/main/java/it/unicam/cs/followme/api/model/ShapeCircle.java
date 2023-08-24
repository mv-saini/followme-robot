package it.unicam.cs.followme.api.model;

/**
 * This class represents a circle.
 *
 * @param radius Radius of the circle.
 * @param label  Name of the circle.
 * @author Mohit Vijay Saini
 * @see ShapeInterface
 */
public record ShapeCircle(double radius, String label) implements ShapeInterface {

    public ShapeCircle {
        if (radius < 0) throw new IllegalArgumentException("Negative radius");
    }

    @Override
    public boolean insideArea(Coordinates c, Coordinates centerShape) {
        //(x - h)² + (y - k)² ≤ r²
        double distance = Math.sqrt(Math.pow(c.x() - centerShape.x(), 2) + Math.pow(c.y() - centerShape.y(), 2));
        return distance <= radius;
    }

    /**
     * Retrieves the radius of the circle.
     *
     * @return the radius of the circle.
     */
    @Override
    public double radius() {
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
