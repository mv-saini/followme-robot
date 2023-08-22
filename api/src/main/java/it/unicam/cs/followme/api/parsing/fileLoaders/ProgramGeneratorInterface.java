package it.unicam.cs.followme.api.parsing.fileLoaders;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.api.utility.LinkedPrograms;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;


/**
 * Any class that implements this interface can load a program file.
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 * @author Mohit Vijay Saini
 */
public interface ProgramGeneratorInterface<R extends RobotInterface<Direction>, S extends ShapeInterface>{

    /**
     * Retrieves a linked list of program which is parsed by the parser from the given file.
     * @param prog the file that contains the programs to be parsed.
     * @return the linked list that contains the parsed programs.
     * @throws FollowMeParserException
     * @throws IOException
     */
    LinkedPrograms<R, S> generateProgram(File prog) throws FollowMeParserException, IOException;

}
