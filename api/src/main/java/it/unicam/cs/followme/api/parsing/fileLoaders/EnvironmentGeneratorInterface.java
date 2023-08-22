package it.unicam.cs.followme.api.parsing.fileLoaders;

import it.unicam.cs.followme.api.model.*;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Any class that implements this interface can load an environment file.
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 * @author Mohit Vijay Saini
 */
public interface EnvironmentGeneratorInterface<R extends RobotInterface<Direction>, S extends ShapeInterface> {

    /**
     * Retrieves an environment which is parsed by the parser from the given file and a map of robots generated with
     * random values.
     * @param env the file that contains the environment to be parsed.
     * @param robots the map of randomly generate robots.
     * @return the environment containing shapes and robots.
     * @throws FollowMeParserException
     * @throws IOException
     */
    EnvironmentInterface<R, S> generateEnvironment(File env, Map<R, Coordinates> robots) throws FollowMeParserException, IOException;

}
