package it.unicam.cs.followme.api.parsing.Program;

import it.unicam.cs.followme.api.model.*;
import it.unicam.cs.followme.api.parsing.files.Handler;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class InitializedProgramsTest {

    @Test
    void terminated_ExecuteNext() throws FollowMeParserException, IOException {
        RobotInterface<Direction> r = new Robot<>(new Direction(10, 10, 10));
        Coordinates c = new Coordinates(5, 1);

        Map<RobotInterface<Direction>, Coordinates> map = new HashMap<>();
        map.put(r, c);

        EnvironmentInterface<RobotInterface<Direction>, ShapeInterface> e = new Environment<>(map, new HashMap<>());

        Handler<RobotInterface<Direction>, ShapeInterface> handler = new Handler<>();

        FollowMeParser parser = new FollowMeParser(handler);

        parser.parseRobotProgram(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("initProgTest.txt")).getFile()));

        assertEquals(1, handler.getProgramList().size());

        InitializedProgramsInterface<RobotInterface<Direction>, ShapeInterface> i = new InitializedPrograms<>(handler.getProgramList(), r);

        assertFalse(i.terminated());

        i.executeNext(e);

        assertEquals("12.07", new DecimalFormat("#.00").format(e.getRobotCoords(r).x()));

        assertEquals("8.07", new DecimalFormat("#.00").format(e.getRobotCoords(r).y()));

        assertTrue(r.getDirection().equals(new Direction(5, 5, 10)));

        assertTrue(i.terminated());
    }
}