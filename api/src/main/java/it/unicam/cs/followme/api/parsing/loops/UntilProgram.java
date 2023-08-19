package it.unicam.cs.followme.api.parsing.loops;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.EnvironmentInterface;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;

/**
 * Until program whose condition is satisfied only when the given robot is inside a shape whose label was provided at
 * the time of parsing.
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 * @author Mohit Vijay Saini
 * @see LoopProgramsInterface
 */
public final class UntilProgram<R extends RobotInterface<Direction>, S extends ShapeInterface> implements LoopProgramsInterface<R, S> {

    /**
     * Label of the shape
     */
    private final String label;

    /**
     * Program counter to jump to.
     */
    private int jumpTo;

    public UntilProgram(String label) {
        this.label = label;
    }

    @Override
    public boolean conditionSatisfied(EnvironmentInterface<R, S> env, R robot) {
        System.out.println(env.robotInsideShapes(robot).contains(this.label));
        return env.robotInsideShapes(robot).contains(this.label);
    }

    @Override
    public void setJumpTo(int jumpTo) {
        this.jumpTo = jumpTo;
    }

    @Override
    public int getJumpTo() {
        return this.jumpTo;
    }

    @Override
    public LoopProgramsInterface<R, S> makeCopy() {
        LoopProgramsInterface<R, S> copied = new UntilProgram<>(this.label);
        copied.setJumpTo(this.getJumpTo());
        return copied;
    }

    @Override
    public String toString() {
        return "UNTIL";
    }
}
