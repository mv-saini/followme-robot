package it.unicam.cs.followme.api.controller;

import it.unicam.cs.followme.api.execution.ExecuteProgram;
import it.unicam.cs.followme.api.fileLoaders.EnvironmentLoader;
import it.unicam.cs.followme.api.fileLoaders.EnvironmentLoaderInterface;
import it.unicam.cs.followme.api.fileLoaders.ProgramLoader;
import it.unicam.cs.followme.api.fileLoaders.ProgramLoaderInterface;
import it.unicam.cs.followme.api.model.*;
import it.unicam.cs.followme.api.parsing.files.Handler;
import it.unicam.cs.followme.api.parsing.Program.InitializedPrograms;
import it.unicam.cs.followme.api.parsing.Program.InitializedProgramsInterface;
import it.unicam.cs.followme.api.parsing.loops.*;
import it.unicam.cs.followme.api.utility.LinkedPrograms;
import it.unicam.cs.followme.api.utility.Program;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This is the main controller class which takes input from view and send instructions to model.
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 * @author Mohit Vijay Saini
 */
public class Controller<R extends RobotInterface<Direction>, S extends ShapeInterface> {

    /**
     * The generated environment.
     */
    private EnvironmentInterface<R, S> environment;

    /**
     * Environment file loader.
     */
    private final EnvironmentLoaderInterface<R, S> environmentLoader;

    /**
     * Program file loader.
     */
    private final ProgramLoaderInterface<R, S> programLoader;

    /**
     * List of copied programs for each robot.
     */
    private List<InitializedProgramsInterface<R, S>> initializedPrograms;

    /**
     * Executor will execute N number of programs inside the list above.
     */
    private ExecuteProgram<R, S> execute;

    /**
     * To generate a controller with R as {@link RobotInterface} and S as {@link ShapeInterface}.
      * @return a controller with file loaders for environment and program with parser and handler.
     */
    public static Controller<RobotInterface<Direction>, ShapeInterface> getController(){

        Handler<RobotInterface<Direction>, ShapeInterface> handler = new Handler<>();

        FollowMeParser parser = new FollowMeParser(handler);

        return new Controller<>(new EnvironmentLoader<>(parser), new ProgramLoader<>(handler, parser));
    }

    /**
     * Creates an instance of this controller class.
     * @param environmentLoader the environment file loader.
     * @param programLoader the program file loader.
     */
    public Controller(EnvironmentLoaderInterface<R, S> environmentLoader, ProgramLoaderInterface<R, S> programLoader) {
        this.environmentLoader = environmentLoader;
        this.environment = new Environment<>(new HashMap<>(), new HashMap<>());
        this.programLoader = programLoader;
        this.initializedPrograms = new LinkedList<>();
    }

    /**
     * Calls the methods to load and parse environment and programs as well as generate random robots.
     * @param env file containing environment.
     * @param prog file containing progrma.
     * @param robotsGenerated number of robots to randomly generate.
     * @throws FollowMeParserException
     * @throws IOException
     */
    public void parseFiles(File env, File prog, Map<R, Coordinates> robotsGenerated)
            throws FollowMeParserException, IOException {

        loadEnvironment(env, robotsGenerated);
        loadProgram(prog);

        System.out.println(this.environment.getRobots().size());
        System.out.println(this.environment.getRobots());
        System.out.println();
        System.out.println(this.environment.getShapes().size());
        System.out.println(this.environment.getShapes());
        System.out.println();
        System.out.println(this.initializedPrograms.size());
        this.initializedPrograms.forEach(InitializedProgramsInterface::print);
    }

    /**
     * Creates an instance of executor.
     */
    public void createExecutor(){
        this.execute = new ExecuteProgram<>(this.environment, this.initializedPrograms);
    }

    /**
     * Tells the executor to execute N number of programs.
     * @param n number of programs to execute.
     * @return the messages informing what was executed.
     */
    public ArrayList<String> execute(int n){
        return new ArrayList<>(this.execute.executeNextSteps(n));

    }

    /**
     * Loads the environment from the file.
     * @param env the file containing environment.
     * @param robotsGenerated map of randomly generated robots.
     * @throws FollowMeParserException
     * @throws IOException
     */
    private void loadEnvironment(File env, Map<R, Coordinates> robotsGenerated)
            throws FollowMeParserException, IOException {

        this.environment = this.environmentLoader.getEnv(env, robotsGenerated);

    }

    /**
     * Loads the programs from the file.
     * @param prog the file containing programs.
     * @throws FollowMeParserException
     * @throws IOException
     */
    private void loadProgram(File prog) throws FollowMeParserException, IOException {
        LinkedPrograms<R, S> programs = this.programLoader.getProgram(prog);
        loadProgramEachRobot(this.environment.getRobots().keySet().stream().toList(), programs);
    }

    /**
     * To make a copy of programs for each robot.
     * @param robots the list of robots.
     * @param programs programs to be copied for each robot.
     */
    private void loadProgramEachRobot(List<R> robots, LinkedPrograms<R, S> programs){
        robots.forEach(r -> this.initializedPrograms.add(new InitializedPrograms<>(makeCopy(programs), r)));
    }

    /**
     * Copy each program.
     * @param toMakeCopyOf the list to make a copy of.
     * @return the copied linked list.
     */
    private LinkedPrograms<R, S> makeCopy(LinkedPrograms<R, S> toMakeCopyOf){

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

    /**
     * Retrieves the environment.
     * @return the environment.
     */
    public EnvironmentInterface<R, S> getEnvironment() {
        return environment;
    }

    /**
     * Retrieves the executor.
     * @return the executor.
     */
    public
    ExecuteProgram<R, S> getExecute() {
        return this.execute;
    }
}
