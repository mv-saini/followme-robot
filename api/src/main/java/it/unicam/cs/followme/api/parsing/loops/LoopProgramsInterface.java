package it.unicam.cs.followme.api.parsing.loops;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.EnvironmentInterface;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;

/**
 * Any class that implements this interface can act as a loop program.
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 */
public interface LoopProgramsInterface<R extends RobotInterface<Direction>, S extends ShapeInterface> {

    /**
     * Determines whether the loop program's condition was satisfied or not.
     * @param env environment that may or may not be required to determine the condition of the loop.
     * @param robot robot that may or may not be required to determine the condition of the loop.
     * @return true if loop is done otherwise false.
     */
    boolean conditionSatisfied(EnvironmentInterface<R, S> env, R robot);

    /**
     * Sets the program counter to the program after the loop.
     * @param jumpTo the program counter to jump to if or when the loop is done.
     */
    void setJumpTo(int jumpTo);

    /**
     * Retrieves the program counter to jump to.
     * @return the program counter.
     */
    int getJumpTo();

    /**
     * Retrieves an exact copy of the loop.
     * @return the copy of the loop.
     */
    LoopProgramsInterface<R, S> makeCopy();
}
