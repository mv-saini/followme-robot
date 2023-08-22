package it.unicam.cs.followme.api.parsing.loops;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoneProgramTest {

    @Test
    void conditionSatisfied() {
        LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface> done = new DoneProgram<>();
        assertTrue(done.conditionSatisfied(null, null));
    }

    @Test
    void makeCopy() {
        LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface> done = new DoneProgram<>();
        done.setJumpTo(5);
        LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface> copied = done.makeCopy();

        assertFalse(copied.equals(done));

        assertEquals(copied.getJumpTo(), done.getJumpTo());
    }
}