package it.unicam.cs.followme.api.utility;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.api.parsing.loops.LoopProgramsInterface;
import it.unicam.cs.followme.utilities.RobotCommand;

import java.util.Arrays;

/**
 * This class represents the structure of a program.
 *
 * @param <R>          Robots that extends {@link RobotInterface}
 * @param <S>          Shapes that extends {@link ShapeInterface}
 * @param programLabel Name of the program. enum -> {@link RobotCommand}
 * @param args         Arguments of the program.
 * @param loopType     Type of loop of the program.
 * @author Mohit Vijay Saini
 */
public record Program<R extends RobotInterface<Direction>, S extends ShapeInterface>(RobotCommand programLabel,
                                                                                     String[] args,
                                                                                     LoopProgramsInterface<R, S> loopType) {

    /**
     * Generates a new program.
     *
     * @param programLabel name of the program.
     * @param args         arguments of the program.
     * @param loopType     loop type of the program.
     */
    public Program {
        if(programLabel == null) throw new IllegalArgumentException("Robot command can't be null");
    }

    /**
     * Retrieves the name of the program.
     *
     * @return the name of the program.
     */
    @Override
    public RobotCommand programLabel() {
        return programLabel;
    }

    /**
     * Retrieves the arguments of the program.
     *
     * @return the arguments of the program.
     */
    @Override
    public String[] args() {
        return args;
    }

    /**
     * Retrieves the loop type of the program.
     *
     * @return the loop type of the program.
     */
    @Override
    public LoopProgramsInterface<R, S> loopType() {
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
}
