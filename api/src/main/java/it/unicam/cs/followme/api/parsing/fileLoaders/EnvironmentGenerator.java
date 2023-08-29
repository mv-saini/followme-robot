package it.unicam.cs.followme.api.parsing.fileLoaders;

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
 * @author Mohit Vijay Saini
 * @see EnvironmentGeneratorInterface
 */
public class EnvironmentGenerator<R extends RobotInterface<Direction>> implements EnvironmentGeneratorInterface<R, ShapeInterface> {

    /**
     * Parser which will parse the environment file.
     */
    private final FollowMeParser parser;

    /**
     * This adds the shapes parsed by the parser to the environment
     */
    private final ShapeParserInterface<ShapeInterface> shapeParser;

    /**
     * Generates the environment file loader.
     * @param parser parser which will be used to parse the file.
     */
    public EnvironmentGenerator(FollowMeParser parser) {
        this.parser = parser;
        this.shapeParser = new ShapeParser();
    }

    @Override
    public EnvironmentInterface<R, ShapeInterface> generateEnvironment(File env, Map<R, Coordinates> robots) throws FollowMeParserException, IOException {
        return new Environment<>(robots, shapeParser.parseShapes(this.parser.parseEnvironment(env)));
    }
}
