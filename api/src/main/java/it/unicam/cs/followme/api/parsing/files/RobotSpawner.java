package it.unicam.cs.followme.api.parsing.files;

import it.unicam.cs.followme.api.model.Coordinates;
import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.Robot;
import it.unicam.cs.followme.api.model.RobotInterface;

import java.util.*;

/**
 * This class generates N number of robots between a pre-defined range (x,y) with random direction and speed.
 */
public class RobotSpawner {

    /**
     * Min value for x and y.
     */
    private final int MIN_RANDOM = -10;

    /**
     * Max value for x and y.
     */
    private final int MAX_RANDOM = 10;

    private final Random random = new Random();

    /**
     * Generates robot with random values.
     * @param n number of robots to generate.
     * @return the generated robots and their coordinates.
     */
    public Map<RobotInterface<Direction>, Coordinates> generateRobots(int n){

        Map<RobotInterface<Direction>, Coordinates> robots = new HashMap<>();

        for(int i = 0; i < n; i++){
            robots.put(new Robot<>(new Direction(random.nextDouble(), random.nextDouble(), random.nextDouble())),
                    new Coordinates(randomGeneratorRange(), randomGeneratorRange())
            );
            //System.out.println(robots.get(i).toString());
        }

        return robots;
    }

    /**
     * Random number between a pre-defined range.
     * @return the number generated.
     */
    private double randomGeneratorRange(){
        return MIN_RANDOM + ((MAX_RANDOM - MIN_RANDOM) * random.nextDouble());
    }

}
