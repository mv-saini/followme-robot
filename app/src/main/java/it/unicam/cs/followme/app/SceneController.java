package it.unicam.cs.followme.app;

import it.unicam.cs.followme.api.controller.Controller;
import it.unicam.cs.followme.api.model.*;
import it.unicam.cs.followme.api.parsing.files.RobotSpawner;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This controller is responsible for what happens on the simulation stage of the project.
 */
public class SceneController implements Initializable {

    /**
     * How much to move when any direction buttons are pressed.
     */
    private final double directionOffset = 100;

    /**
     * Size of the robot on the screen.
     */
    private final double robotSize = 2;

    /**
     * The area representing the environment.
     */
    @FXML
    public Pane drawingArea;

    /**
     * Group where all the buttons are located.
     */
    @FXML
    public ButtonBar buttonBar;

    /**
     * Area where the steps label is located.
     */
    @FXML
    public Pane stepsPane;

    /**
     * To show what happened when the next button is pressed.
     */
    @FXML
    public TextFlow logger = new TextFlow();

    /**
     * List containing the shapes and number of robots that have been added to the environment.
     */
    private final ArrayList<String> loggerList = new ArrayList<>();

    /**
     * List containing all the programs that have been executed.
     */
    private final ArrayList<String> programsExecuted = new ArrayList<>();

    /**
     * Text field that contains the number of steps to be executed the next time, next button is pressed.
     */
    public TextField stepsText;

    /**
     * Offset of the drawing area (environment).
     */
    private Coordinates offset;

    /**
     * Controller that connects the view and the model.
     */
    Controller<RobotInterface<Direction>, ShapeInterface> controller = Controller.getController();

    /**
     * Group containing all the shapes and the robots.
     */
    private final Group contentGroup = new Group();

    /**
     * Zoom level of the drawing area.
     */
    private double zoomLevel = 1.0;

    /**
     * Center of the drawing area.
     */
    private Coordinates center;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.offset = new Coordinates(0, 0);
        setCenter();
        this.drawingArea.getChildren().add(contentGroup);
        styling();
        setZoomScroll();
        this.buttonBar.toFront();
        this.logger.toFront();
    }

    /**
     * Sets the center based on the height and width of the drawing area.
     */
    private void setCenter(){
        double deltaX = this.drawingArea.getPrefWidth() / 2;
        double deltaY = this.drawingArea.getPrefHeight() / 2;
        this.center = new Coordinates(deltaX, deltaY);
    }

    /**
     * Styling of the elements.
     */
    private void styling(){
        this.buttonBar.setStyle("-fx-alignment: CENTER;");
        this.logger.setStyle("-fx-background-color: gray;");
        this.drawingArea.setStyle("-fx-background-color: #D3D3D3");
        this.buttonBar.setStyle("-fx-background-color: gray;");
        this.stepsPane.setStyle("-fx-background-color: gray;");
    }

    /**
     * Controls the zoom in and out of the drawing area with scroll wheel and calls a method to redraw the elements.
     */
    private void setZoomScroll(){
        drawingArea.setOnScroll(event -> {
            double zoomFactor = 1.5;

            if (event.getDeltaY() < 0) zoomLevel /= zoomFactor;
            else zoomLevel *= zoomFactor;

            contentGroup.setScaleX(zoomLevel);
            contentGroup.setScaleY(zoomLevel);
            drawElements();
        });
    }

    /**
     * Moves everything to the left by changing the offset and calls a method to redraw the elements.
     * @param event
     */
    @FXML
    private void moveLeft(ActionEvent event) {
        this.offset = this.offset.addCoordinates(new Coordinates(this.directionOffset * -1, 0));
        drawElements();
    }

    /**
     * Moves everything up by changing the offset and calls a method to redraw the elements.
     * @param event
     */
    @FXML
    private void moveUp(ActionEvent event) {
        this.offset = this.offset.addCoordinates(new Coordinates(0, this.directionOffset * -1));
        drawElements();
    }

    /**
     * Moves everything down by changing the offset and calls a method to redraw the elements.
     * @param event
     */
    @FXML
    private void moveDown(ActionEvent event) {
        this.offset = this.offset.addCoordinates(new Coordinates(0, this.directionOffset));
        drawElements();
    }

    /**
     * Moves everything to the right by changing the offset and calls a method to redraw the elements.
     * @param event
     */
    @FXML
    private void moveRight(ActionEvent event) {
        this.offset = this.offset.addCoordinates(new Coordinates(this.directionOffset, 0));
        drawElements();
    }

    /**
     * Resets everything.
     * @param actionEvent
     */
    @FXML
    public void clearScene(ActionEvent actionEvent) {
        this.controller = Controller.getController();
        helperClearScene();
        this.programsExecuted.clear();
    }

    /**
     * Separate clear just to redraw elements instead of resetting everything.
     */
    private void helperClearScene() {
        this.contentGroup.getChildren().clear();
        this.logger.getChildren().clear();
        this.loggerList.clear();
    }

    /**
     * Executes next n programs. if steps text field is empty or '0' was provided then nothing is executed.
     * @param actionEvent
     */
    @FXML
    public void executeNext(ActionEvent actionEvent) {
        if(this.controller.getExecute() == null)
            return;

        if(this.stepsText.getText().isEmpty() || this.stepsText.getText().strip().equals("0"))
            this.programsExecuted.add("Nothing was executed.");
        else this.programsExecuted.addAll(this.controller.execute(Integer.parseInt(this.stepsText.getText())));

        drawElements();
    }

    /**
     * Opens another stage where the user can load text files for environment and program and specify how many robots
     * to spawn.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void loadScene(ActionEvent actionEvent) throws IOException {
        clearScene(null);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("loadfile.fxml"));
        Parent parent = loader.load();
        Stage loaderStage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        loaderStage.setScene(new Scene(parent));
        loaderStage.setResizable(false);
        loaderStage.show();
    }

    /**
     * Calls the controller to parse the files provided by the user.
     * @param env environment file.
     * @param program program file.
     * @param robots number of robots to spawn.
     * @throws FollowMeParserException
     * @throws IOException
     */
    public void parseFiles(File env, File program, int robots) throws FollowMeParserException, IOException {
        RobotSpawner robotSpawner = RobotSpawner.createDefaultGenerator();
        this.controller.parseFiles(env, program, robotSpawner.generateRobots(robots));
        this.controller.createExecutor();
        drawElements();
    }

    /**
     * Draws the shapes and robots.
     */
    private void drawElements(){
        helperClearScene();
        drawShapes();
        drawRobots();
        this.loggerList.forEach(s -> this.logger.getChildren().add(new Text(s + "\n\n")));
        this.logger.getChildren().add(new Text("--------------------\n\n"));
        this.programsExecuted.forEach(s -> this.logger.getChildren().add(new Text(s + "\n\n")));
    }

    /**
     * Draws the robots.
     */
    private void drawRobots(){
        this.controller.getEnvironment().getRobots().forEach((key, value) -> {
            Coordinates scaled = new Coordinates(value.getX(), value.getY() * -1).addCoordinates(center.addCoordinates(offset));

            Circle c = new Circle(scaled.getX() * this.zoomLevel, scaled.getY() * this.zoomLevel,
                    robotSize * this.zoomLevel, Color.BLACK);

            contentGroup.getChildren().add(c);
        });
        loggerList.add("Added " + this.controller.getEnvironment().getRobots().size() + " robots");
    }

    /**
     * Draws the shapes.
     */
    private void drawShapes(){
        this.controller.getEnvironment().getShapes().forEach((key, value) -> {
            if(key instanceof ShapeCircle circle){
                drawCircle(value, circle);
            }
            else if(key instanceof ShapeRectangle rectangle){
                drawRectangle(value, rectangle);
            }
        });
    }

    /**
     * Draws a rectangle.
     * @param value coordinates of the center of the rectangle.
     * @param rectangle rectangle shape containing its width and height.
     */
    private void drawRectangle(Coordinates value, ShapeRectangle rectangle) {
        //scales the coordinates according to the offset and the center.
        Coordinates scaled = new Coordinates(value.getX(), value.getY() * -1).addCoordinates(center.addCoordinates(offset));

        //coordinates of the top left corner of the rectangle.
        double x_tl = scaled.getX() - (rectangle.getWidth() / 2);
        double y_tl = scaled.getY() - (rectangle.getHeight() / 2);

        Rectangle r = new Rectangle(x_tl * this.zoomLevel, y_tl * this.zoomLevel,
                rectangle.getWidth() * this.zoomLevel, rectangle.getHeight() * this.zoomLevel);
        r.setFill(javafx.scene.paint.Color.BLUE);

        loggerList.add("Added a rectangle");

        contentGroup.getChildren().add(r);
    }

    /**
     * Draws a circle.
     * @param value coordinates of the center of the circle.
     * @param circle circle shape containing its radius.
     */
    private void drawCircle(Coordinates value, ShapeCircle circle) {
        //scales the coordinates according to the offset and the center.
        Coordinates scaled = new Coordinates(value.getX(), value.getY() * -1).addCoordinates(center.addCoordinates(offset));

        Circle c = new Circle(scaled.getX() * this.zoomLevel, scaled.getY() * this.zoomLevel,
                circle.getRadius() * this.zoomLevel, javafx.scene.paint.Color.RED);

        loggerList.add("Added a circle");

        contentGroup.getChildren().add(c);
    }
}
