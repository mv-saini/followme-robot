package it.unicam.cs.followme.api.execution;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.EnvironmentInterface;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.api.execution.Program.InitializedProgramsInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for executing the programs by calling the instances of {@link InitializedProgramsInterface}
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 * @author Mohit Vijay Saini
 */
public class ExecuteProgram<R extends RobotInterface<Direction>, S extends ShapeInterface> {

    /**
     * Environment which will be affected by the programs.
     */
    private final EnvironmentInterface<R, S> environment;

    /**
     * List containing all the instances of the copied programs for each robot.
     */
    private final List<InitializedProgramsInterface<R, S>> initializedPrograms;

    /**
     * Generates an executor.
     * @param environment the environment that contains the shapes and robots.
     * @param initializedPrograms the list contains copied list of programs for each robot.
     */
    public ExecuteProgram(EnvironmentInterface<R, S> environment, List<InitializedProgramsInterface<R, S>> initializedPrograms) {
        this.environment = environment;
        this.initializedPrograms = initializedPrograms;
    }

    /**
     * Executes next N steps (programs).
     * @param n number of next programs to execute.
     * @return messages informing what was executed.
     */
    public ArrayList<String> executeNextSteps(int n){
        ArrayList<String> allThoseJustExecuted = new ArrayList<>();
        for(int i = 0; i < n; i++){
            for (InitializedProgramsInterface<R, S> initializedProgram : this.initializedPrograms) {
                if(!initializedProgram.terminated()){
                    allThoseJustExecuted.add(initializedProgram.executeNext(this.environment));
                }
            }
        }
        if(allThoseJustExecuted.isEmpty()) allThoseJustExecuted.add("ALL ROBOTS TERMINATED THEIR PROGRAMS");
        allThoseJustExecuted.add("--------------------");
        return allThoseJustExecuted;
    }

}
