package it.unicam.cs.followme.api.parsing.fileLoaders;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.api.parsing.files.Handler;
import it.unicam.cs.followme.api.parsing.loops.DoneProgram;
import it.unicam.cs.followme.api.parsing.loops.UntilProgram;
import it.unicam.cs.followme.api.utility.LinkedPrograms;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ProgramGeneratorTest {

    @Test
    void generateProgram() throws FollowMeParserException, IOException {
        Handler<RobotInterface<Direction>, ShapeInterface> handler = new Handler<>();
        FollowMeParser parser = new FollowMeParser(handler);

        ProgramGeneratorInterface<RobotInterface<Direction>, ShapeInterface> gen = new ProgramGenerator<>(handler, parser);

        LinkedPrograms<RobotInterface<Direction>, ShapeInterface> prog = gen.generateProgram(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("testProg.txt")).getFile()));

        assertEquals(14, prog.size());

        int contUntil = 0;

        for(int i = 0; i < handler.getProgramList().size(); i++){
            if(handler.getProgramList().getNodeLoopType(i) instanceof UntilProgram){
                contUntil++;
            }
        }

        assertEquals(contUntil, 4);

    }
}