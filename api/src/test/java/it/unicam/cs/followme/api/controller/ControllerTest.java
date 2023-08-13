package it.unicam.cs.followme.api.controller;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.api.parsing.files.RobotSpawner;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

class ControllerTest {

    @Test
    void something() throws FollowMeParserException, IOException {
        Controller<RobotInterface<Direction>, ShapeInterface> c = Controller.getController();
        RobotSpawner spawner = new RobotSpawner();
        File env = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("testEnv.txt")).getFile());
        File prog = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("testProg.txt")).getFile());
        c.parseFiles(env, prog, spawner.generateRobots(10));
    }
}