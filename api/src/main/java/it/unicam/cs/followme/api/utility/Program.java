package it.unicam.cs.followme.api.utility;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.api.parsing.loops.LoopProgramsInterface;
import it.unicam.cs.followme.utilities.RobotCommand;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents the structure of a program.
 *
 * @author Mohit Vijay Saini
 */
public final class Program<R extends RobotInterface<Direction>, S extends ShapeInterface> {

    private final RobotCommand programLabel;

    private final String[] args;

    private final LoopProgramsInterface<R, S> loopType;


    /**
     * Generates a new program.
     *
     * @param programLabel name of the program.
     * @param args         arguments of the program.
     * @param loopType     loop type of the program.
     */
    public Program(RobotCommand programLabel, String[] args, LoopProgramsInterface<R, S> loopType) {
        if (programLabel == null) throw new IllegalArgumentException("Robot command can't be null");
        this.programLabel = programLabel;
        this.args = args;
        this.loopType = loopType;
    }

    /**
     * Retrieves the name of the program.
     *
     * @return the name of the program.
     */
    public RobotCommand getProgramLabel() {
        return programLabel;
    }

    /**
     * Retrieves the arguments of the program.
     *
     * @return the arguments of the program.
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Retrieves the loop type of the program.
     *
     * @return the loop type of the program.
     */
    public LoopProgramsInterface<R, S> getLoopType() {
        return loopType;
    }

    @Override
    public String toString() {
        return "Program{" +
                "programLabel=" + programLabel +
                ", args=" + Arrays.toString(args) +
                ", loopType=" + loopType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program<?, ?> program = (Program<?, ?>) o;
        return programLabel == program.programLabel && Arrays.equals(args, program.args) && Objects.equals(loopType, program.loopType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(programLabel, loopType);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}
