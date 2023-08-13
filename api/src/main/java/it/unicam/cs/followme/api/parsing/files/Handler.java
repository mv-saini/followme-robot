package it.unicam.cs.followme.api.parsing.files;

import it.unicam.cs.followme.api.model.Direction;
import it.unicam.cs.followme.api.model.RobotInterface;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.api.utility.LinkedPrograms;
import it.unicam.cs.followme.api.parsing.loops.*;
import it.unicam.cs.followme.api.utility.Program;
import it.unicam.cs.followme.utilities.FollowMeParserHandler;
import it.unicam.cs.followme.utilities.RobotCommand;

import java.util.*;

/**
 * This class implements the {@link FollowMeParserHandler} interface which allows this class to parse a program file.
 * All the parsed programs are stored in the {@link LinkedPrograms} linked list. This class uses a stack to parse the
 * loop programs. Whenever a loop is read its program counter is added to the stack which is only removed when a done
 * command is found.
 * Parses:
 * <ul>
 *     <li>MOVE</li>
 *     <li>SIGNAL</li>
 *     <li>UNSIGNAL</li>
 *     <li>FOLLOW</li>
 *     <li>CONTINUE</li>
 *     <li>STOP</li>
 *     <li>REPEAT</li>
 *     <li>UNTIL</li>
 *     <li>DOFOREVER</li>
 *     <li>DONE</li>
 * </ul>
 * @param <R> Robots that extends {@link RobotInterface}
 * @param <S> Shapes that extends {@link ShapeInterface}
 */
public class Handler<R extends RobotInterface<Direction>, S extends ShapeInterface> implements FollowMeParserHandler {

    private LinkedPrograms<R, S> programList;

    private Stack<Integer> stack;

    /**
     * Retrieves the linked list containing the programs.
     * @return
     */
    public LinkedPrograms<R, S> getProgramList() {
        return programList;
    }

    /**
     * Generates a handler.
     */
    public Handler() {
        init();
    }

    @Override
    public void parsingStarted() {
        init();
    }

    /**
     * Initialize the linked list and the stack.
     */
    private void init() {
        this.programList = new LinkedPrograms<>();
        this.stack = new Stack<>();
    }

    @Override
    public void parsingDone() {
        if(!stack.isEmpty()) throw new IllegalArgumentException("Program file was not parsed correctly. A loop was found without done closure.");
    }

    /**
     * Converts the arguments of the program from double to string.
     * @param args
     * @return
     */
    public String[] commandHelperDoubleToString(double[] args) {
        return Arrays.stream(args)
                .mapToObj(Double::toString)
                .toArray(String[]::new);
    }

    @Override
    public void moveCommand(double[] args) {
        this.programList.add(new Program<>(RobotCommand.MOVE, commandHelperDoubleToString(args), null));
    }

    @Override
    public void moveRandomCommand(double[] args) {
        this.programList.add(new Program<>(RobotCommand.MOVE, commandHelperDoubleToString(args), null));
    }

    @Override
    public void signalCommand(String label) {
        this.programList.add(new Program<>(RobotCommand.SIGNAL, new String[]{label}, null));
    }

    @Override
    public void unsignalCommand(String label) {
        this.programList.add(new Program<>(RobotCommand.SIGNAL, new String[]{label}, null));
    }

    @Override
    public void followCommand(String label, double[] args) {
        this.programList.add(new Program<>(RobotCommand.FOLLOW, commandHelperDoubleToString(args), null));
    }

    @Override
    public void stopCommand() {
        this.programList.add(new Program<>(RobotCommand.STOP, new String[0], null));
    }

    /**
     * Continue method is handled as a repeat program where s seconds represents the number of times the robot must execute
     * move command, going towards the same direction at the same speed.
     * @param s number of seconds;
     */
    @Override
    public void continueCommand(int s) {
        repeatCommandStart(s);
        moveCommand(new double[0]);
        doneCommand();
    }

    @Override
    public void repeatCommandStart(int n) {
        LoopProgramsInterface<R, S> repeat = new RepeatProgram<>(n);
        this.programList.add(new Program<>(RobotCommand.REPEAT, new String[]{String.valueOf(n)}, repeat));
        stack.push(this.programList.size() - 1);
    }

    @Override
    public void untilCommandStart(String label) {
        UntilProgram<R, S> until = new UntilProgram<>(label);
        this.programList.add(new Program<>(RobotCommand.UNTIL, new String[]{label}, until));
        stack.push(this.programList.size() - 1);
    }

    @Override
    public void doForeverStart() {
        ForeverProgram<R, S> forever = new ForeverProgram<>();
        this.programList.add(new Program<>(RobotCommand.FOREVER, new String[0], forever));
        stack.push(this.programList.size() - 1);
    }

    @Override
    public void doneCommand() {
        DoneProgram<R, S> done = new DoneProgram<>();
        done.setJumpTo(stack.peek());
        this.programList.add(new Program<>(RobotCommand.DONE, new String[0], done));
        this.programList.getNodeLoopType(stack.pop()).setJumpTo(this.programList.size());
    }
}
