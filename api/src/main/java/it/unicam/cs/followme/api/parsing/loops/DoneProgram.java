package it.unicam.cs.followme.api.parsing.loops;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.EnvironmentInterface;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;

/**
 * Done program that always jumps to the start of its loop.
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 * @author Mohit Vijay Saini
 * @see LoopProgramsInterface
 */
public final class DoneProgram<R extends RobotInterface<Direction>, S extends ShapeInterface> implements LoopProgramsInterface<R, S> {

    /**
     * Program counter to jump to.
     */
    int jumpTo = 0;

    @Override
    public boolean conditionSatisfied(EnvironmentInterface<R, S> env, R robot) {
        return true;
    }

    @Override
    public void setJumpTo(int jumpTo) {
        this.jumpTo = jumpTo;
    }

    @Override
    public int getJumpTo(){
        return this.jumpTo;
    }

    @Override
    public LoopProgramsInterface<R, S> makeCopy() {
        LoopProgramsInterface<R, S> copied = new DoneProgram<>();
        copied.setJumpTo(this.getJumpTo());
        return copied;
    }

    @Override
    public String toString() {
        return "DoneProgram{" +
                "jumpTo=" + jumpTo +
                '}';
    }
}
