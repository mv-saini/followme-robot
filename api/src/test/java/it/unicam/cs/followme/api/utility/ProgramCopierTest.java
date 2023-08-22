package it.unicam.cs.followme.api.utility;

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

class ProgramCopierTest {

    @Test
    void makeCopy() throws FollowMeParserException, IOException {
        Handler<RobotInterface<Direction>, ShapeInterface> handler = new Handler<>();

        FollowMeParser parser = new FollowMeParser(handler);

        parser.parseRobotProgram(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("testProg.txt")).getFile()));

        ProgramCopier<RobotInterface<Direction>, ShapeInterface> copier = new ProgramCopier<>();

        LinkedPrograms<RobotInterface<Direction>, ShapeInterface> copied = copier.makeCopy(handler.getProgramList());

        assertFalse(copied.equals(handler.getProgramList()));

        assertEquals(copied.size(), handler.getProgramList().size());
    }
}