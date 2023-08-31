package it.unicam.cs.followme.api.parsing.loops;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.EnvironmentInterface;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;

import java.util.Objects;

/**
 * Repeat program whose condition is satisfied only when the program and its sub programs are executed given number of times.
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 * @author Mohit Vijay Saini
 * @see LoopProgramsInterface
 */
public final class RepeatProgram<R extends RobotInterface<Direction>, S extends ShapeInterface> implements LoopProgramsInterface<R, S> {

    /**
     * Program to repeat for given times.
     */
    private final int repeatFor;

    /**
     * Times it has been repeated.
     */
    private int counter;

    /**
     * Program counter to jump to.
     */
    private int jumpTo;

    public RepeatProgram(int repeatFor) {
        this.repeatFor = repeatFor;
        this.counter = 0;
    }

    @Override
    public boolean conditionSatisfied(EnvironmentInterface<R, S> env, R robot) {
        if(this.counter < this.repeatFor){
            this.counter++;
            return false;
        }
        else{
            this.counter = 0;
        }
        return true;
    }

    @Override
    public void setJumpTo(int jumpTo){
        this.jumpTo = jumpTo;
    }

    @Override
    public int getJumpTo() {
        return jumpTo;
    }

    @Override
    public LoopProgramsInterface<R, S> makeCopy() {
        LoopProgramsInterface<R, S> copied = new RepeatProgram<>(this.repeatFor);
        copied.setJumpTo(this.getJumpTo());
        return copied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepeatProgram<?, ?> that = (RepeatProgram<?, ?>) o;
        return repeatFor == that.repeatFor && counter == that.counter && jumpTo == that.jumpTo;
    }

    @Override
    public String toString() {
        return "REPEAT";
    }
}
