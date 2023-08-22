package it.unicam.cs.followme.api.parsing.loops;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForeverProgramTest {

    @Test
    void conditionSatisfied() {
        LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface> forever = new ForeverProgram<>();
        assertFalse(forever.conditionSatisfied(null, null));
    }

    @Test
    void makeCopy() {
        LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface> forever = new ForeverProgram<>();
        forever.setJumpTo(5);
        LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface> copied = forever.makeCopy();

        assertFalse(copied.equals(forever));

        assertEquals(copied.getJumpTo(), forever.getJumpTo());
    }
}