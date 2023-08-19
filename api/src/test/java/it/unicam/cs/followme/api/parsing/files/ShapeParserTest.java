package it.unicam.cs.followme.api.parsing.files;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import it.unicam.cs.followme.utilities.ShapeData;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ShapeParserTest {

    @Test
    void test() throws FollowMeParserException, IOException {
        Handler<RobotInterface<Direction>, ShapeInterface> handler = new Handler<>();
        FollowMeParser parser = new FollowMeParser(handler);

        List<ShapeData> list = parser.parseEnvironment(new File(Objects.requireNonNull(getClass().getClassLoader().getResource("testEnv.txt")).getFile()));

        assertEquals(list.size(), 4);

        assertEquals(list.stream()
                .filter(shapeData -> shapeData.shape().equals("CIRCLE"))
                .toList()
                .size(), 2);

        assertEquals(list.stream()
                .filter(shapeData -> shapeData.shape().equals("RECTANGLE"))
                .toList()
                .size(), 2);
    }
}