package it.unicam.cs.followme.api.controller;

import it.unicam.cs.followme.api.model.*;
import it.unicam.cs.followme.api.parsing.files.RobotSpawner;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void TestApp() throws FollowMeParserException, IOException {
        Controller<RobotInterface<Direction>, ShapeInterface> controller = Controller.getController();

        RobotSpawner spawner = RobotSpawner.createDefaultGenerator();

        Map<RobotInterface<Direction>, Coordinates> robotsGenerated = spawner.generateRobots(3);

        File envFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("testEnv.txt")).getFile());
        File progFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("testProg.txt")).getFile());

        controller.parseFiles(envFile, progFile, robotsGenerated);
        
        controller.createExecutor();

        assertEquals(3, controller.getEnvironment().getRobots().size());

        assertEquals(4, controller.getEnvironment().getShapes().size());
        
        assertNotNull(controller.getEnvironment());
    }
}