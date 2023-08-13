package it.unicam.cs.followme.api.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class represents an environment that contains robots, shapes and their coordinates.
 * @param <R> robots
 * @param <S> shapes
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
        return this.robots.entrySet().stream()
                .filter(entry -> entry.getKey().getLabel().equals(labelToFollow))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public double distanceBetween(Coordinates c1, Coordinates c2){
        return Math.sqrt(Math.pow(c1.getX() - c2.getX(), 2) + Math.pow(c1.getY() - c2.getY(), 2));
    }

    @Override
    public Coordinates averageOf(List<Coordinates> avgOf){
        return new Coordinates(
                avgOf.stream().collect(Collectors.averagingDouble(Coordinates::getX)),
                avgOf.stream().collect(Collectors.averagingDouble(Coordinates::getY))
        );
    }


    @Override
    public Coordinates getRobotCoords(R robot){
        return this.robots.get(robot);
    }

    @Override
    public Set<String> robotInsideShapes(R robot) {
        return this.shapes.entrySet().stream()
                .filter(entry -> entry.getKey().insideArea(this.robots.get(robot), entry.getValue()))
                .map(Map.Entry::getKey)
                .map(ShapeInterface::getLabel)
                .collect(Collectors.toSet());
    }

    @Override
    public void update(R robot, Coordinates c1){
        this.robots.replace(robot, c1);
    }
}
