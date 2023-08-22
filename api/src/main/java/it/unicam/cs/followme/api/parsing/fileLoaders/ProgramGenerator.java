package it.unicam.cs.followme.api.parsing.fileLoaders;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.api.parsing.files.Handler;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import it.unicam.cs.followme.api.utility.LinkedPrograms;

import java.io.File;
import java.io.IOException;

/**
 * This class loads the program file to be parsed.
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 * @author Mohit Vijay Saini
 * @see ProgramGeneratorInterface
 */
public class ProgramGenerator<R extends RobotInterface<Direction>, S extends ShapeInterface> implements ProgramGeneratorInterface<R, S> {

    /**
     * The handler which will retrieve the parsed list.
     */
    Handler<R, S> handler;

    /**
     * Parser which will parse the given program file.
     */
    FollowMeParser parser;

    /**
     * Generates the program file loader.
     * @param handler handler which will retrieve the list of parsed programs.
     * @param parser parser which will parse the program file.
     */
    public ProgramGenerator(Handler<R, S> handler, FollowMeParser parser) {
        this.handler = handler;
        this.parser = parser;
    }

    @Override
    public LinkedPrograms<R, S> generateProgram(File prog) throws FollowMeParserException, IOException {
        this.parser.parseRobotProgram(prog);
        return this.handler.getProgramList();
    }
}
