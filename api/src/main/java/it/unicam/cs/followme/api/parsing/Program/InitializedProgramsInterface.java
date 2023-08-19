package it.unicam.cs.followme.api.parsing.Program;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.EnvironmentInterface;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;

/**
 * Any class that implements this interface can calculate the movement of the robot.
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 * @author Mohit Vijay Saini
 */
public interface InitializedProgramsInterface<R extends RobotInterface<Direction>, S extends ShapeInterface> {

    /**
     * Determines whether the program is finished or not.
     * @return true if all the programs has been executed otherwise false.
     */
    boolean terminated();

    /**
     * Next program to execute.
     * @param env the environment that contains the shapes and robots.
     * @return a message informing what was executed.
     */
    String executeNext(EnvironmentInterface<R, S> env);

    void print();
}
