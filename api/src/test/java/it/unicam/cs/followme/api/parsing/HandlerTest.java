package it.unicam.cs.followme.api.parsing;

import it.unicam.cs.followme.api.parsing.files.Handler;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

class HandlerTest {

    @Test
    void test() throws FollowMeParserException, IOException {
        Handler handler = new Handler();

        FollowMeParser parser = new FollowMeParser(handler);

        System.out.println();

        parser.parseRobotProgram(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("testProg.txt")).getFile()));
    }


}