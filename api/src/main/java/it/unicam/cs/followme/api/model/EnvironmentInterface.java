package it.unicam.cs.followme.api.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Any class that implements this interface can create an environment made of shapes and robots.
 * @param <R> Robots
 * @param <S> Shapes
 */
public interface EnvironmentInterface<R extends RobotInterface<Direction>, S extends ShapeInterface> {

    /**
     * This method retrieves a map of robots and their coordinates.
     * @return a map of robots and their coordinates in the environment.
     */
    Map<R, Coordinates> getRobots();

    /**
     * This method retrieves a map of shapes and their coordinates.
     * @return a map of shapes and their coordinates in the environment.
     */
    Map<S, Coordinates> getShapes();

    /**
     * This method retrieves a map of robots with a specific label and their coordinates.
     * @param labelToFollow robots with label.
     * @return a map of robots with the given label and their coordinates in the environment.
     */
    Map<R, Coordinates> robotsWithLabel(String labelToFollow);

    /**
     * Calculates the Euclidean distance between two given coordinates.
     * √((x1 - x2)^2 + (y1 - y2)^2)
     * @param c1 first coordinate.
     * @param c2 second coordinate.
     * @return the distance between two given coordinates.
     */
    double distanceBetween(Coordinates c1, Coordinates c2);

    /**
     * Calculates the average of given coordinates.
     * @param avgOf coordinates whose average is requested.
     * @return the coordinate with average x and y.
     */
    Coordinates averageOf(List<Coordinates> avgOf);

    /**
     * Coordinates of the given robot in the environment.
     * @param robot robot whose coordinates are requested.
     * @return the coordinates of the given robot.
     */
    Coordinates getRobotCoords(R robot);

    /**
     * Labels of all the shapes that have the robot inside their area.
     * @param robot the robot which may or may not be in the area of shape/s.
     * @return the set of labels.
     */
    Set<String> robotInsideShapes(R robot);

    /**
     * Updates the given robots coordinates.
     * @param robot the robot which moved.
     * @param c1 the new coordinates.
     */
    void update(R robot, Coordinates c1);
}
