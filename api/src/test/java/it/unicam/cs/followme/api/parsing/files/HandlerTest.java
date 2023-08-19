package it.unicam.cs.followme.api.parsing.files;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.api.parsing.files.Handler;
import it.unicam.cs.followme.api.parsing.loops.DoneProgram;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandlerTest {

    @Test
    void test() throws FollowMeParserException, IOException {
        Handler<RobotInterface<Direction>, ShapeInterface> handler = new Handler<>();

        FollowMeParser parser = new FollowMeParser(handler);

        parser.parseRobotProgram(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("testProg.txt")).getFile()));

        assertEquals(handler.getProgramList().size(), 14);

        int contDone = 0;

        for(int i = 0; i < handler.getProgramList().size(); i++){
            if(handler.getProgramList().getNodeLoopType(i) instanceof DoneProgram){
                contDone++;
            }
        }

        assertEquals(contDone, 5);
    }


}