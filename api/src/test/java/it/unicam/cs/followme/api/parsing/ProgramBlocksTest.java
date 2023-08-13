package it.unicam.cs.followme.api.parsing;

import it.unicam.cs.followme.api.utility.LinkedPrograms;
import it.unicam.cs.followme.api.parsing.loops.LoopProgramsInterface;
import it.unicam.cs.followme.api.parsing.loops.RepeatProgram;
import it.unicam.cs.followme.api.utility.Program;
import it.unicam.cs.followme.api.model.*;
import it.unicam.cs.followme.utilities.RobotCommand;
import org.junit.jupiter.api.Test;

import java.util.*;

class ProgramBlocksTest {
/*
    @Test
    void proceedSpecific(){
        Random r = new Random();
        Direction d = new Direction(r.nextDouble(), r.nextDouble(), r.nextDouble());
        Coordinates coord = new Coordinates(1 ,1 );
        RobotInterface<Direction> robot = new Robot<>(d);
        Map<RobotInterface<Direction>, Coordinates> robotsGenerated = new HashMap<>();
        robotsGenerated.put(robot, coord);
        EnvironmentInterface<RobotInterface<Direction>, Shape> env = new Environment<>(robotsGenerated, new HashMap<>());

        String[] args = {"10", "10", "1"};
        Program pro = new Program(0, RobotCommand.MOVE, args);
        List<Program> l = new LinkedList<>();
        l.add(pro);

        ProgramBlocksInterface<RobotInterface<Direction>, Shape> p = new ProgramBlocks<>(null,
                1, null, l, robot);

        p.executeNext(env, 1);

        assertEquals("1.7", String.format("%.1f", env.getRobotCoords(robot).get().getX()));
    }

    @Test
    void proceedSpecificWithSpeed(){
        Random r = new Random();
        Direction d = new Direction(r.nextDouble(), r.nextDouble(), r.nextDouble());
        Coordinates coord = new Coordinates(1 ,1 );
        RobotInterface<Direction> robot = new Robot<>(d);
        Map<RobotInterface<Direction>, Coordinates> robotsGenerated = new HashMap<>();
        robotsGenerated.put(robot, coord);
        EnvironmentInterface<RobotInterface<Direction>, Shape> env = new Environment<>(robotsGenerated, new HashMap<>());

        String[] args = {"10", "10", "2"};
        Program pro = new Program(0, RobotCommand.MOVE, args);
        List<Program> l = new LinkedList<>();
        l.add(pro);

        ProgramBlocksInterface<RobotInterface<Direction>, Shape> p = new ProgramBlocks<>(null,
                1, null, l, robot);

        p.executeNext(env, 1);

        assertEquals("2.4", String.format("%.1f", env.getRobotCoords(robot).get().getX()));
    }
*/
    @Test
    void testRepeat(){
        Random r = new Random();
        Direction d = new Direction(r.nextDouble(), r.nextDouble(), r.nextDouble());
        Coordinates coord = new Coordinates(1 ,1 );
        RobotInterface<Direction> robot = new Robot<>(d);
        Map<RobotInterface<Direction>, Coordinates> robotsGenerated = new HashMap<>();
        robotsGenerated.put(robot, coord);
        EnvironmentInterface<RobotInterface<Direction>, ShapeInterface> env = new Environment<>(robotsGenerated, new HashMap<>());

        LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface> rep = new RepeatProgram(5);
        Program<LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface>> repeat = new Program<LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface>>(RobotCommand.REPEAT, new String[]{String.valueOf(5)}, rep);

        String[] args = {"10", "10", "2"};
        Program<LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface>> move = new Program<LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface>>(RobotCommand.MOVE, args, null);

        Program<LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface>> done = new Program<LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface>>(RobotCommand.DONE, new String[0], null);

        LinkedPrograms<Program<LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface>>> list = new LinkedPrograms<>();
        list.add(repeat);
        list.add(move);
        list.add(done);
    }
}