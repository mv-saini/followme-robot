/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package it.unicam.cs.followme.api.utility;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.api.parsing.loops.LoopProgramsInterface;
import it.unicam.cs.followme.api.parsing.loops.RepeatProgram;
import it.unicam.cs.followme.api.utility.LinkedPrograms;
import it.unicam.cs.followme.api.utility.Program;
import it.unicam.cs.followme.utilities.RobotCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkedProgramsTest {

    @Test void testAddSizeGetters() {
        LinkedPrograms<RobotInterface<Direction>, ShapeInterface> list = new LinkedPrograms<>();

        list.add(new Program<>(
                RobotCommand.MOVE, new String[]{"5", "5", "5"}, null
        ));
        assertEquals(1, list.size());
        assertEquals(RobotCommand.MOVE, list.getNodeRobotCommand(0));

        list.add(new Program<>(
                RobotCommand.REPEAT, new String[]{"5"},
                new RepeatProgram<>(5)
        ));
        list.getNodeLoopType(1).setJumpTo(4);
        assertEquals(2, list.size());
        assertEquals(RobotCommand.REPEAT, list.getNodeRobotCommand(1));
        LoopProgramsInterface<RobotInterface<Direction>, ShapeInterface> r = new RepeatProgram<>(5);
        r.setJumpTo(4);
        assertTrue(list.getNodeLoopType(1).equals(r));
    }
}
