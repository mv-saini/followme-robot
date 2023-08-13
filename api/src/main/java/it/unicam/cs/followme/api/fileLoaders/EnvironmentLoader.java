package it.unicam.cs.followme.api.fileLoaders;

import it.unicam.cs.followme.api.model.*;
import it.unicam.cs.followme.api.parsing.files.ShapeParser;
import it.unicam.cs.followme.api.parsing.files.ShapeParserInterface;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * This class loads the environment file to be parsed.
 * @param <R> Robots that extends {@link RobotInterface}
 */
public class EnvironmentLoader<R extends RobotInterface<Direction>> implements EnvironmentLoaderInterface<R, ShapeInterface> {

    /**
     * Parser which will parse the environment file.
     */
    private final FollowMeParser parser;

    /**
     * This adds the shapes parsed by the parser to the environment
     */
    private final ShapeParserInterface shapeParser;

    /**
     * Generates the environment file loader.
     * @param parser parser which will be used to parse the file.
     */
    public EnvironmentLoader(FollowMeParser parser) {
        this.parser = parser;
        this.shapeParser = new ShapeParser();
    }

    @Override
    public EnvironmentInterface<R, ShapeInterface> getEnv(File env, Map<R, Coordinates> robots) throws FollowMeParserException, IOException {
        return new Environment<>(robots, shapeParser.parseShapes(this.parser.parseEnvironment(env)));
    }
}
