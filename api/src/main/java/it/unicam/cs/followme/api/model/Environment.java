package it.unicam.cs.followme.api.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents an environment that contains robots, shapes and their coordinates.
 * @param <R> robots
 * @param <S> shapes
 * @author Mohit Vijay Saini
 * @see EnvironmentInterface
 */
public class Environment<R extends RobotInterface<Direction>, S extends ShapeInterface> implements EnvironmentInterface<R, S> {

    /**
     * Contains the robots and their coordinates.
     */
    private final Map<R, Coordinates> robots;

    /**
     * Contains the shapes and their coordinates.
     */
    private final Map<S, Coordinates> shapes;

    public Environment(Map<R, Coordinates> robots, Map<S, Coordinates> shapes) {
        if(robots == null || shapes == null) throw new IllegalArgumentException("Robots or Shapes are null");
        this.robots = robots;
        this.shapes = shapes;
    }

    @Override
    public Map<R, Coordinates> getRobots() {
        return this.robots;
    }

    @Override
    public Map<S, Coordinates> getShapes() {
        return this.shapes;
    }

    @Override
    public Map<R, Coordinates> robotsWithLabel(String labelToFollow){
        if(labelToFollow == null)
            throw new IllegalArgumentException("The provided label is null.");

        return this.robots.entrySet().stream()
                .filter(entry -> entry.getKey().getLabel().equals(labelToFollow))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public double distanceBetween(Coordinates c1, Coordinates c2){
        if(c1 == null || c2 == null)
            throw new IllegalArgumentException("One of two coordinates provided were null.");

        return Math.sqrt(Math.pow(c1.x() - c2.x(), 2) + Math.pow(c1.y() - c2.y(), 2));
    }

    @Override
    public Coordinates averageOf(List<Coordinates> avgOf){
        if(avgOf.isEmpty())
            throw new IllegalArgumentException("List is empty.");

        return new Coordinates(
                avgOf.stream().collect(Collectors.averagingDouble(Coordinates::x)),
                avgOf.stream().collect(Collectors.averagingDouble(Coordinates::y))
        );
    }


    @Override
    public Coordinates getRobotCoords(R robot){
        return this.robots.get(robot);
    }

    @Override
    public Set<String> robotInsideShapes(R robot) {
        if(this.robots.get(robot) == null)
            throw new IllegalArgumentException("No such robot in the environment.");
        return this.shapes.entrySet().stream()
                .filter(entry -> entry.getKey().insideArea(this.robots.get(robot), entry.getValue()))
                .map(Map.Entry::getKey)
                .map(ShapeInterface::label)
                .collect(Collectors.toSet());
    }

    @Override
    public void update(R robot, Coordinates c1){
        this.robots.replace(robot, c1);
    }
}
