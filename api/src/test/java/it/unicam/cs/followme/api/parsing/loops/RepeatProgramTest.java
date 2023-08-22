package it.unicam.cs.followme.api.parsing.loops;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepeatProgramTest {

    @Test
    void conditionSatisfied() {
        LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface> repeat = new RepeatProgram<>(5);
        for(int i = 0; i < 5; i++) assertFalse(repeat.conditionSatisfied(null, null));
        assertTrue(repeat.conditionSatisfied(null, null));
    }

    @Test
    void makeCopy() {
        LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface> repeat = new RepeatProgram<>(5);
        repeat.setJumpTo(5);
        LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface> copied = repeat.makeCopy();

        assertTrue(copied.equals(repeat));
        copied.conditionSatisfied(null, null);
        assertFalse(copied.equals(repeat));

        assertEquals(copied.getJumpTo(), repeat.getJumpTo());
    }
}