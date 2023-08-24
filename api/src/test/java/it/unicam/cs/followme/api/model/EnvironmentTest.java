package it.unicam.cs.followme.api.model;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentTest {

    @Test
    void robotsWithLabel() {
        Direction d1 = new Direction(5,5,5);
        Direction d2 = new Direction(-9, 8 , 6);
        Direction d3 = new Direction(1,1,1 );

        RobotInterface<Direction> r1 = new Robot<>(d1);
        RobotInterface<Direction> r2 = new Robot<>(d2);
        RobotInterface<Direction> r3 = new Robot<>(d3);

        r1.setLabel("label1");
        r2.setLabel("label2");
        r3.setLabel("label2");

        Map<RobotInterface<Direction>, Coordinates> map = new HashMap<>();

        /*map.putAll(label1);
        map.putAll(label2);*/

        map.put(r1, new Coordinates(5,5));
        map.put(r2, new Coordinates(5,5));
        map.put(r3, new Coordinates(5,5));

        EnvironmentInterface<RobotInterface<Direction>, ShapeInterface> env = new Environment<>(map, new HashMap<>());

        Map<RobotInterface<Direction>, Coordinates> p1 = env.robotsWithLabel("label1");

        Map<RobotInterface<Direction>, Coordinates> p2 = env.robotsWithLabel("label2");

        if(p1.containsKey(r2)) fail();
        if(!p1.containsKey(r1)) fail();
        if(!p2.containsKey(r2)) fail();
        if(!p2.containsKey(r3)) fail();
        if(p2.containsKey(r1)) fail();

        /*assertNotEquals(label2, env.robotsWithLabel("label1"));
        assertEquals(label2, env.robotsWithLabel("label2"));
        assertNotEquals(label1, env.robotsWithLabel("label2"));*/
    }

    @Test
    void distanceBetween() {
        EnvironmentInterface<RobotInterface<Direction>, ShapeInterface> env = new Environment<>(new HashMap<>(), new HashMap<>());
        Coordinates c1 = new Coordinates(8, 0);
        Coordinates c2 = new Coordinates(5, 0);
        assertEquals(3, env.distanceBetween(c1, c2));
        c1 = new Coordinates(8, 9);
        c2 = new Coordinates(5, -3);
        assertEquals("12.37", new DecimalFormat("#.00").format(env.distanceBetween(c1, c2)));
    }

    @Test
    void averageOf() {
        EnvironmentInterface<RobotInterface<Direction>, ShapeInterface> env = new Environment<>(new HashMap<>(), new HashMap<>());
        Coordinates expected = new Coordinates(3.75, 2.25);
        List<Coordinates> list = new LinkedList<>();
        list.add(new Coordinates(8, 9));
        list.add(new Coordinates(5, -3));
        list.add(new Coordinates(8, -5));
        list.add(new Coordinates(-6, 8));
        assertEquals(expected.x(), env.averageOf(list).x());
        assertEquals(expected.y(), env.averageOf(list).y());
    }

    @Test
    void robotInsideShapes() {
        ShapeInterface c1 = new ShapeCircle(5, "c1");
        ShapeInterface c2 = new ShapeCircle(3, "c2");
        ShapeInterface r1 = new ShapeRectangle(5,6,"r1");

        Map<ShapeInterface, Coordinates> shapes = new HashMap<>();
        shapes.put(c1, new Coordinates(0,0));
        shapes.put(c2, new Coordinates(0,6));
        shapes.put(r1, new Coordinates(2.5,3));

        Direction d1 = new Direction(5,5,5);
        RobotInterface<Direction> robot2 = new Robot<>(d1); //all 3
        robot2.setLabel("c1");
        RobotInterface<Direction> robot1 = new Robot<>(d1); //circles
        robot1.setLabel("c2");
        RobotInterface<Direction> robot4 = new Robot<>(d1); //circle rectangel
        robot4.setLabel("c2");
        RobotInterface<Direction> robot3 = new Robot<>(d1); //center circle rectangle
        robot3.setLabel("r1");

        Map<RobotInterface<Direction>, Coordinates> robots = new HashMap<>();
        robots.put(robot2, new Coordinates(2.2, 4.2));
        robots.put(robot1, new Coordinates(-1, 4));
        robots.put(robot3, new Coordinates(3,3));
        robots.put(robot4, new Coordinates(2.5, 5.5));

        EnvironmentInterface<RobotInterface<Direction>, ShapeInterface> env = new Environment<>(robots, shapes);

        Set<String> robotsInShapes = env.robotInsideShapes(robot2);
        Set<String> all3 = new HashSet<>();
        Set<String> allC = new HashSet<>();
        Set<String> CR = new HashSet<>();
        Set<String> BCR = new HashSet<>();
        all3.add("c1");
        all3.add("c2");
        all3.add("r1");
        allC.add("c1");
        allC.add("c2");
        CR.add("c2");
        CR.add("r1");
        BCR.add("c1");
        BCR.add("r1");

        if(!robotsInShapes.containsAll(all3)) fail();

        robotsInShapes = env.robotInsideShapes(robot1);

        if(!robotsInShapes.containsAll(allC)) fail();

        robotsInShapes = env.robotInsideShapes(robot4);

        if(!robotsInShapes.containsAll(CR)) fail();

        robotsInShapes = env.robotInsideShapes(robot3);

        if(!robotsInShapes.containsAll(BCR)) fail();
    }
}