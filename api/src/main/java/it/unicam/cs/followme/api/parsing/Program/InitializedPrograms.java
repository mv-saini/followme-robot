package it.unicam.cs.followme.api.parsing.Program;

import it.unicam.cs.followme.api.utility.LinkedPrograms;
import it.unicam.cs.followme.api.parsing.loops.LoopProgramsInterface;
import it.unicam.cs.followme.api.model.*;
import it.unicam.cs.followme.utilities.RobotCommand;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class contains a robot and its own copy of the program and program counter.
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 * @author Mohit Vijay Saini
 * @see InitializedProgramsInterface
 */
public class InitializedPrograms<R extends RobotInterface<Direction>, S extends ShapeInterface>
        implements InitializedProgramsInterface<R, S> {

    /**
     * program counter
     */
    private int runningCounter;

    /**
     * copy of the program list from the parser.
     */
    private final LinkedPrograms<R, S> programList;

    /**
     * robot from the list of all robots from the environment.
     */
    private final R robot;

    /**
     * Create an instance of this class with a robot and its own program list.
     * @param programList the program list associated with this robot.
     * @param robot the robot to which all the programs are applied.
     */
    public InitializedPrograms(LinkedPrograms<R, S> programList, R robot) {
        this.runningCounter = 0;
        this.programList = programList;
        this.robot = robot;
    }

    @Override
    public boolean terminated(){
        return this.runningCounter >= this.programList.size();
    }

    @Override
    public String executeNext(EnvironmentInterface<R, S> env){
        if(this.runningCounter == this.programList.size())
            return null;
        RobotCommand robotCommand = this.programList.getNodeRobotCommand(this.runningCounter);

        if(nextIsLoop(robotCommand)) return executeLoop(robotCommand, env);
        else return executeBasic(robotCommand, env);
    }

    /**
     * Determine if the next program is a loop or not.
     * @param programLabel the next program name.
     * @return true if loop otherwise false -> basic program.
     */
    private boolean nextIsLoop(RobotCommand programLabel) {
        switch(programLabel){
            case REPEAT, UNTIL, FOREVER, DONE -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * calculates the movement of the robot.
     * @param programLabel the program name.
     * @param env the environment that is affected.
     * @return a message informing what was executed.
     */
    private String executeBasic(RobotCommand programLabel, EnvironmentInterface<R,S> env) {
        switch (programLabel){
            case MOVE -> {
                return proceed(env, this.programList.getNodeArgs(this.runningCounter));
            }
            case SIGNAL -> {
                return signal(env, this.programList.getNodeArgs(this.runningCounter));
            }
            case UNSIGNAL -> {
                return unsignal(env, this.programList.getNodeArgs(this.runningCounter));
            }
            case FOLLOW -> {
                return follow(env, this.programList.getNodeArgs(this.runningCounter));
            }
            case STOP -> {
                return halt(env);
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Enters a loop program.
     * @param programLabel name of the loop program.
     * @param env the environment that will be affected.
     * @return a message informing what was executed.
     */
    private String executeLoop(RobotCommand programLabel, EnvironmentInterface<R, S> env) {
        switch (programLabel){
            case UNTIL -> {
                return untilLoop(env, this.programList.getNodeLoopType(this.runningCounter));
            }
            case REPEAT -> {
                return repeatLoop(env, this.programList.getNodeLoopType(this.runningCounter));
            }
            case FOREVER -> {
                return foreverLoop(env, this.programList.getNodeLoopType(this.runningCounter));
            }
            case DONE -> {
                return endLoop(env, this.programList.getNodeLoopType(this.runningCounter));
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * This method calls one of the MOVE program. if it has more than 3 arguments then it is a move towards a random
     * direction otherwise it is a move towards a specific direction.
     * @param env the environment that is affected.
     * @param args the arguments of the move program.
     * @return a message informing the movement of robot towards the given direction.
     */
    private String proceed(EnvironmentInterface<R,S> env, String[] args) {
        if(args.length == 0) return proceedUtil(env, this.robot.getDirection());
        else if(args.length < 4) return proceedSpecific(env, args);
        else return proceedRandom(env, args);
    }

    /**
     * This is a move towards a specific direction.
     * @param env the environment that is affected.
     * @param args the arguments of the move program.
     * @return a message informing the movement of robot towards the given direction.
     */
    private String proceedSpecific(EnvironmentInterface<R,S> env, String[] args) {
        return proceedUtil(env, new Direction(Double.parseDouble(args[0]),
                Double.parseDouble(args[1]), Double.parseDouble(args[2])));
    }

    /**
     * This is a move towards a random direction.
     * @param env the environment that is affected.
     * @param args the arguments of the move program.
     * @return a message informing the movement of robot towards a random direction within the given limit.
     */
    private String proceedRandom(EnvironmentInterface<R,S> env, String[] args) {
        return proceedUtil(env, new Direction(randomValueGenerated(args[0], args[1]),
                randomValueGenerated(args[2], args[3]), Double.parseDouble(args[4])
        ));
    }

    /**
     * This a shared method between move random and move specific direction.
     * @param env the environment that is affected.
     * @param direction the direction towards the robot will move.
     * @return a message informing the movement of robot towards the given direction.
     */
    private String proceedUtil(EnvironmentInterface<R,S> env, Direction direction){
        Coordinates current = env.getRobotCoords(this.robot);
        this.robot.setDirection(direction);
        env.update(this.robot, new Coordinates(
                current.getX() + (direction.getX() * direction.getSpeed()),
                current.getY() + (direction.getY() * direction.getSpeed())
        ));
        this.runningCounter++;
        return "Moved a robot towards " + direction;
    }


    /**
     * Generates random values for the move random program.
     * @param min min value for x and y
     * @param max max value for x and y
     * @return the value generated.
     */
    private double randomValueGenerated(String min, String max){
        Random rand = new Random();
        double minD = Double.parseDouble(min);
        double maxD = Double.parseDouble(max);
        return minD + ((maxD - minD) * rand.nextDouble());
    }

    /**
     * This signals the robot to show the given label.
     * @param env the environment that is affected.
     * @param args the arguments of the signal program.
     * @return a message informing that the robot is signalling the given label.
     */
    private String signal(EnvironmentInterface<R,S> env, String[] args) {
        this.robot.setLabel(args[0]);
        env.update(this.robot, env.getRobotCoords(robot));
        this.runningCounter++;
        return "A robot is signaling " + args[0];
    }

    /**
     * This tells the robot to stop showing the given label.
     * @param env the environment that is affected.
     * @param args the arguments of the unsignal program.
     * @return a message informing that the robot has stopped signalling the given label.
     */
    private String unsignal(EnvironmentInterface<R,S> env, String[] args) {
        System.out.println("YO WHATS UP");
        if(this.robot.getLabel().equals(args[0])) this.robot.resetLabel();
        env.update(this.robot, env.getRobotCoords(robot));
        this.runningCounter++;
        return "A robot has stopped signaling " + args[0];
    }

    /**
     * The robot follows the direction of the robots with a specific label and their distance to the robot.
     * @param env the environment that will be affected.
     * @param args the arguments of the follow program.
     * @return a message informing the movement of the robot.
     */
    private String follow(EnvironmentInterface<R,S> env, String[] args) {
        Coordinates robotCoords = env.getRobotCoords(this.robot);
        //Map<R, Coordinates> robotsWithLabel = env.robotsWithLabel(args[0]);
        List<Coordinates> avgOf = env.robotsWithLabel(args[0]).values().stream()
                .filter(coordinates -> env.distanceBetween(robotCoords, coordinates) <= Double.parseDouble(args[1]))
                .collect(Collectors.toCollection(LinkedList::new));
        /*List<Coordinates> avgOf = new LinkedList<>();
        robotsWithLabel.forEach((r, coordinates) -> {
            if(Double.parseDouble(args[1]) >= env.distanceBetween(robotCoords, coordinates)){
                avgOf.add(coordinates);
            }
        });*/
        Coordinates avg = env.averageOf(avgOf);
        if(!avgOf.isEmpty()){
            return proceedSpecific(env, new String[]{String.valueOf(avg.getX()), String.valueOf(avg.getY()), args[2]});
        }
        else{
            return proceedRandom(env, new String[]{
                    String.valueOf(Double.parseDouble(args[1]) * -1),
                    args[1],
                    String.valueOf(Double.parseDouble(args[1]) * -1),
                    args[1],
                    args[2]
            });
        }
        //this.runningCounter++;
    }

    /**
     * This stops the movement of the robot.
     * @param env the environment that is affected.
     * @return a message informing the robot has stopped its movement.
     */
    private String halt(EnvironmentInterface<R,S> env) {
        this.robot.getDirection().setSpeed(0);
        signal(env, new String[0]);
        return "A robot has stopped";
    }

    /**
     * This is the until loop which calls the helper method.
     * @param env the environment that will be affected.
     * @param loopProgram the loop program containing the condition to exit the loop.
     * @return a message informing the state of the loop or robot's movement.
     */
    private String untilLoop(EnvironmentInterface<R, S> env, LoopProgramsInterface<R, S> loopProgram) {
        return loopUtil(env, loopProgram);
    }

    /**
     * This is the repeat loop which calls the helper method.
     * @param env the environment that will be affected.
     * @param loopProgram the loop program containing the condition to exit the loop.
     * @return a message informing the state of the loop or robot's movement.
     */
    private String repeatLoop(EnvironmentInterface<R, S> env, LoopProgramsInterface<R, S> loopProgram) {
        return loopUtil(env, loopProgram);
    }

    /**
     * This is the do forever loop which calls the helper method.
     * @param env the environment that will be affected.
     * @param loopProgram the loop program containing the condition to exit the loop.
     * @return a message informing the state of the loop or robot's movement.
     */
    private String foreverLoop(EnvironmentInterface<R, S> env, LoopProgramsInterface<R, S> loopProgram) {
        return loopUtil(env, loopProgram);
    }

    /**
     * This is the helper method which checks the if the condition of the loop has been satisfied or not.
     * if true then run the program after loop otherwise continue with the loop.
     * @param env the environment that will be affected.
     * @param loopProgram the loop program containing the condition to exit the loop.
     * @return a message informing the state of the loop or robot's movement.
     */
    private String loopUtil(EnvironmentInterface<R, S> env, LoopProgramsInterface<R, S> loopProgram){
        if(!loopProgram.conditionSatisfied(env, this.robot)){
            this.runningCounter++;
            return "Looping " + loopProgram + "\n\n" + executeNext(env);
        }
        else this.runningCounter = loopProgram.getJumpTo();
        return "Ended " + loopProgram + " loop"+ "\n\n" + executeNext(env);
    }

    /**
     * This is the done closure of a loop which points to the start of its loop to check if the condition has been
     * satisfied or not.
     * @param env the environment that will be affected.
     * @param doneProgram the loop program containing the condition to exit the loop.
     * @return a message informing the state of the loop or robot's movement.
     */
    private String endLoop(EnvironmentInterface<R, S> env, LoopProgramsInterface<R, S> doneProgram) {
        if(doneProgram.conditionSatisfied(null, null)){
            this.runningCounter = doneProgram.getJumpTo();
            return "Ended a cycle\n\n" + executeNext(env);
        }
        return "DONE";
    }

    @Override
    public void print() {
        for(int i = 0; i < this.programList.size(); i++){
            if(this.programList.getNodeLoopType(i) != null) {
                System.out.println(i + " " + this.programList.getNodeRobotCommand(i) + " " + Arrays.toString(this.programList.getNodeArgs(i)) + " " +
                        this.programList.getNodeLoopType(i).getJumpTo());
            }
            else{
                System.out.println(i + " " + this.programList.getNodeRobotCommand(i) + " " + Arrays.toString(this.programList.getNodeArgs(i)) + " " +
                        null);
            }
        }
    }
}
