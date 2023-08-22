package it.unicam.cs.followme.api.utility;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.api.parsing.loops.LoopProgramsInterface;

/**
 * The class is responsible for making an individual copy of LinkedPrograms for each robot
 * @param <R>
 * @param <S>
 */
public class ProgramCopier<R extends RobotInterface<Direction>, S extends ShapeInterface> {

    /**
     * Copy each program.
     * @param toMakeCopyOf the list to make a copy of.
     * @return the copied linked list.
     */
    public LinkedPrograms<R, S> makeCopy(LinkedPrograms<R, S> toMakeCopyOf){

        LinkedPrograms<R, S> copied = new LinkedPrograms<>();

        for(int i = 0; i < toMakeCopyOf.size(); i++) addCopy(copied, toMakeCopyOf, i);

        return copied;
    }

    /**
     * Adds the copy a single program.
     * @param copied the list containing the copied programs.
     * @param toMakeCopyOf the list to make a copy of.
     * @param i the program counter of the program to be copied.
     */
    private void addCopy(LinkedPrograms<R, S> copied,
                         LinkedPrograms<R, S> toMakeCopyOf, int i){

        LoopProgramsInterface<R, S> loop = toMakeCopyOf.getNodeLoopType(i);
        String[] args = toMakeCopyOf.getNodeArgs(i);

        if(loop != null) copied.add(new Program<>(toMakeCopyOf.getNodeRobotCommand(i), args.clone(), loop.makeCopy()));
        else copied.add(new Program<>(toMakeCopyOf.getNodeRobotCommand(i), args.clone(), null));
    }


}
