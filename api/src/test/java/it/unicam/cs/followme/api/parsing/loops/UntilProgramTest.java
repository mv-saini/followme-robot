package it.unicam.cs.followme.api.parsing.loops;

import it.unicam.cs.followme.api.model.*;
import it.unicam.cs.followme.api.parsing.files.Handler;
import it.unicam.cs.followme.api.parsing.files.RobotSpawner;
import it.unicam.cs.followme.api.parsing.files.ShapeParser;
import it.unicam.cs.followme.api.parsing.files.ShapeParserInterface;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import it.unicam.cs.followme.utilities.ShapeData;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class UntilProgramTest {

    @Test
    void conditionSatisfied() throws FollowMeParserException, IOException {
        Handler<RobotInterface<Direction>, ShapeInterface> handler = new Handler<>();
        FollowMeParser parser = new FollowMeParser(handler);

        ShapeParserInterface shapeParser = new ShapeParser();

        List<ShapeData> list = parser.parseEnvironment(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("testEnv.txt")).getFile()));

        RobotSpawner spawn = new RobotSpawner();

        RobotInterface<Direction> r1 = new Robot<>(new Direction(5, 5, 5));
        Coordinates c = new Coordinates(-100, -100);

        Map<RobotInterface<Direction>, Coordinates> map = spawn.generateRobots(5);
        map.put(r1, c);

        EnvironmentInterface<RobotInterface<Direction>, ShapeInterface> e = new Environment<>(map, shapeParser.parseShapes(list));

        LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface> until = new UntilProgram<>("C1");
        assertFalse(until.conditionSatisfied(e, r1));
        r1.setLabel("C1");
        e.update(r1, new Coordinates(0, 2));
        assertTrue(until.conditionSatisfied(e, r1));
    }

    @Test
    void makeCopy() throws FollowMeParserException, IOException {

        Handler<RobotInterface<Direction>, ShapeInterface> handler = new Handler<>();
        FollowMeParser parser = new FollowMeParser(handler);

        ShapeParserInterface shapeParser = new ShapeParser();

        List<ShapeData> list = parser.parseEnvironment(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("testEnv.txt")).getFile()));

        RobotSpawner spawn = new RobotSpawner();

        RobotInterface<Direction> r1 = new Robot<>(new Direction(5, 5, 5));
        Coordinates c = new Coordinates(-100, -100);

        Map<RobotInterface<Direction>, Coordinates> map = spawn.generateRobots(5);
        map.put(r1, c);

        EnvironmentInterface<RobotInterface<Direction>, ShapeInterface> e = new Environment<>(map, shapeParser.parseShapes(list));

        LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface> until = new UntilProgram<>("C1");
        until.setJumpTo(5);
        LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface> copied = until.makeCopy();

        assertFalse(copied.equals(until));

        r1.setLabel("C1");
        e.update(r1, new Coordinates(0, 2));

        assertTrue(copied.conditionSatisfied(e, r1));

        assertEquals(copied.getJumpTo(), until.getJumpTo());
    }

}