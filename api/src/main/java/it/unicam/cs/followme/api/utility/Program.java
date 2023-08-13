package it.unicam.cs.followme.api.utility;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.api.parsing.loops.LoopProgramsInterface;
import it.unicam.cs.followme.utilities.RobotCommand;

import java.util.Arrays;

/**
 * This class represents the structure of a program.
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 */
public class Program<R extends RobotInterface<Direction>, S extends ShapeInterface> {

    /**
     * Name of the program. enum -> {@link RobotCommand}
     */
    private RobotCommand programLabel;

    /**
     * Arguments of the program.
     */
    private String[] args;

    /**
     * Type of loop of the program.
     */
    private LoopProgramsInterface<R, S> loopType;

    /**
     * Generates a new program.
     * @param programLabel name of the program.
     * @param args arguments of the program.
     * @param loopType loop type of the program.
     */
    public Program(RobotCommand programLabel, String[] args, LoopProgramsInterface<R, S> loopType) {
        this.programLabel = programLabel;
        this.args = args;
        this.loopType = loopType;
    }

    /**
     * Retrieves the name of the program.
     * @return the name of the program.
     */
    public RobotCommand getProgramLabel() {
        return programLabel;
    }

    /**
     * Retrieves the arguments of the program.
     * @return the arguments of the program.
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Retrieves the loop type of the program.
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
}
