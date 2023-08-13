package it.unicam.cs.followme.api.parsing.loops;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.EnvironmentInterface;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;

/**
 * DoForever program whose condition is never satisfied.
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 */
public final class ForeverProgram<R extends RobotInterface<Direction>, S extends ShapeInterface> implements LoopProgramsInterface<R, S> {

    /**
     * Program counter to jump to.
     */
    int jumpTo = 0;

    @Override
    public boolean conditionSatisfied(EnvironmentInterface<R, S> env, R robot) {
        return false;
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
        LoopProgramsInterface<R, S> copied = new ForeverProgram<>();
        copied.setJumpTo(this.getJumpTo());
        return copied;
    }

    @Override
    public String toString() {
        return "FOREVER";
    }
}
