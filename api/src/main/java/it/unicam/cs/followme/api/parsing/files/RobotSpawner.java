package it.unicam.cs.followme.api.parsing.files;

import it.unicam.cs.followme.api.model.Coordinates;
import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.Robot;
import it.unicam.cs.followme.api.model.RobotInterface;

import java.util.*;

/**
 * This function interface generates n number of robots between a pre-defined range (x,y) with random direction and speed.
 * @author Mohit Vijay Saini
 */
@FunctionalInterface
public interface RobotSpawner {

    /**
     * Generates a map of robots along with their coordinates.
     * @param n the number of robots to generate.
     * @return the generated robots and their coordinates.
     */
    Map<RobotInterface<Direction>, Coordinates> generateRobots(int n);

    /**
     * Creates a default robot generator instance. Uses a lambda expression to generate robots.
     * @return a default RobotSpawner instance.
     */
    static RobotSpawner createDefaultGenerator() {
        return (n) -> {
            Map<RobotInterface<Direction>, Coordinates> robots = new HashMap<>();
            Random random = new Random();

            for (int i = 0; i < n; i++) {
                robots.put(new Robot<>(new Direction(random.nextDouble(), random.nextDouble(), random.nextDouble())),
                        new Coordinates(randomGeneratorRange(random), randomGeneratorRange(random)));
            }

            return robots;
        };
    }

    /**
     * Generates a random number within a pre-defined range.
     * @param random the instance to generate random numbers within the range.
     * @return a randomly generated double.
     */
    private static double randomGeneratorRange(Random random) {
        int MIN_RANDOM = -10;
        int MAX_RANDOM = 10;
        return MIN_RANDOM + ((MAX_RANDOM - MIN_RANDOM) * random.nextDouble());
    }

}
