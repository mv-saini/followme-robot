package it.unicam.cs.followme.api.parsing.fileLoaders;

import it.unicam.cs.followme.api.model.*;
import it.unicam.cs.followme.api.parsing.files.Handler;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentGeneratorTest {

    @Test
    void generateEnvironment() throws FollowMeParserException, IOException {
        Handler<RobotInterface<Direction>, ShapeInterface> handler = new Handler<>();
        FollowMeParser parser = new FollowMeParser(handler);
        EnvironmentGeneratorInterface<RobotInterface<Direction>, ShapeInterface> gen = new EnvironmentGenerator<>(parser);

        RobotInterface<Direction> r1 = new Robot<>(new Direction(10, 10, 10));
        RobotInterface<Direction> r2 = new Robot<>(new Direction(10, 10, 10));
        RobotInterface<Direction> r3 = new Robot<>(new Direction(10, 10, 10));
        Coordinates c = new Coordinates(5, 1);

        Map<RobotInterface<Direction>, Coordinates> map = new HashMap<>();
        map.put(r1, c);
        map.put(r2, c);
        map.put(r3, c);

        EnvironmentInterface<RobotInterface<Direction>, ShapeInterface> e =
                gen.generateEnvironment(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("testEnv.txt")).getFile()), map);

        assertNotNull(e);

        assertEquals(4, e.getShapes().size());

        assertEquals(2, e.getShapes().entrySet().stream().filter(x -> x.getKey() instanceof ShapeCircle).count());

        assertEquals(3, e.getRobots().size());
    }
}