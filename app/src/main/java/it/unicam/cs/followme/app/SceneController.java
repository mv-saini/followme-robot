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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class SceneController implements Initializable {

    private final double directionOffset = 100;

    private final double robotSize = 2;

    @FXML
    public Pane drawingArea;

    @FXML
    public ButtonBar buttonBar;

    @FXML
    public Pane emptyPane;

    @FXML
    public TextFlow logger = new TextFlow();

    private final ArrayList<String> loggerList = new ArrayList<>();

    private final ArrayList<String> programsExecuted = new ArrayList<>();
    public TextField stepsText;

    private Coordinates offset;

    Controller<RobotInterface<Direction>, ShapeInterface> controller = Controller.getController();

    private final Group contentGroup = new Group();

    private double zoomLevel = 1.0;

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
        /*try {
            parseFiles(
                    new File("C:\\Users\\snamv\\Desktop\\followme-robot\\app\\src\\main\\resources\\testEnv.txt"),
                    new File("C:\\Users\\snamv\\Desktop\\followme-robot\\app\\src\\main\\resources\\testProg.txt"),
                    1
            );
        } catch (FollowMeParserException | IOException e) {
            //throw new RuntimeException(e);
        }*/
        /*Set<RobotInterface<Direction>> s = this.controller.getEnvironment().getRobots().keySet();
        s.forEach(r -> this.controller.getEnvironment().getRobots().get(r).setxy(
                new Coordinates(1,1)
        ));*/

    }

    private void setCenter(){
        double deltaX = this.drawingArea.getPrefWidth() / 2;
        double deltaY = this.drawingArea.getPrefHeight() / 2;
        this.center = new Coordinates(deltaX, deltaY);
    }

    private void styling(){
        this.buttonBar.setStyle("-fx-alignment: CENTER;");
        this.logger.setStyle("-fx-background-color: gray;");
        this.drawingArea.setStyle("-fx-background-color: #D3D3D3");
        this.buttonBar.setStyle("-fx-background-color: gray;");
        this.emptyPane.setStyle("-fx-background-color: gray;");
    }

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

    @FXML
    private void moveLeft(ActionEvent event) {
        this.offset = this.offset.addCoordinates(new Coordinates(this.directionOffset * -1, 0));
        drawElements();
    }

    @FXML
    private void moveUp(ActionEvent event) {
        this.offset = this.offset.addCoordinates(new Coordinates(0, this.directionOffset * -1));
        drawElements();
    }

    @FXML
    private void moveDown(ActionEvent event) {
        this.offset = this.offset.addCoordinates(new Coordinates(0, this.directionOffset));
        drawElements();
    }

    @FXML
    private void moveRight(ActionEvent event) {
        this.offset = this.offset.addCoordinates(new Coordinates(this.directionOffset, 0));
        drawElements();
    }

    @FXML
    public void clearScene(ActionEvent actionEvent) {
        //this.controller = Controller.getController();
        this.contentGroup.getChildren().clear();
        this.logger.getChildren().clear();
        this.loggerList.clear();
    }

    @FXML
    public void executeNext(ActionEvent actionEvent) {
        if(this.controller.getExecute() == null)
            return;

        if(stepsText.getText().isEmpty() || stepsText.getText().strip().equals("0"))
            this.programsExecuted.addAll(this.controller.execute(1));
        else this.programsExecuted.addAll(this.controller.execute(Integer.parseInt(stepsText.getText())));

        drawElements();
    }

    @FXML
    public void loadScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("loadfile.fxml"));
        Parent parent = loader.load();
        Stage loaderStage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
        loaderStage.setScene(new Scene(parent));
        loaderStage.setResizable(false);
        loaderStage.show();
    }

    public void parseFiles(File env, File program, int robots) throws FollowMeParserException, IOException {
        RobotSpawner robotSpawner = new RobotSpawner();
        this.controller.parseFiles(env, program, robotSpawner.generateRobots(robots));
        this.controller.createExecutor();
        drawElements();
    }

    private void drawElements(){
        clearScene(null);
        drawShapes();
        drawRobots();
        this.loggerList.forEach(s -> this.logger.getChildren().add(new Text(s + "\n\n")));
        this.logger.getChildren().add(new Text("--------------------\n\n"));
        this.programsExecuted.forEach(s -> this.logger.getChildren().add(new Text(s + "\n\n")));
    }

    private void drawRobots(){
        this.controller.getEnvironment().getRobots().forEach((key, value) -> {
            System.out.println(key + " "+ value);
            Coordinates scaled = value.addCoordinates(center.addCoordinates(offset));

            Circle c = new Circle(scaled.getX() * this.zoomLevel, scaled.getY() * this.zoomLevel,
                    robotSize * this.zoomLevel, Color.GREEN);

            contentGroup.getChildren().add(c);
        });
        loggerList.add("Added " + this.controller.getEnvironment().getRobots().size() + " robots");
    }

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

    private void drawRectangle(Coordinates value, ShapeRectangle rectangle) {
        Coordinates scaled = new Coordinates(value.getX(), value.getY() * -1).addCoordinates(center.addCoordinates(offset));

        double x_tl = scaled.getX() - (rectangle.getWidth() / 2);
        double y_tl = scaled.getY() - (rectangle.getHeight() / 2);

        Rectangle r = new Rectangle(x_tl * this.zoomLevel, y_tl * this.zoomLevel,
                rectangle.getWidth() * this.zoomLevel, rectangle.getHeight() * this.zoomLevel);
        r.setFill(javafx.scene.paint.Color.BLUE);

        loggerList.add("Added a rectangle");

        contentGroup.getChildren().add(r);
    }

    private void drawCircle(Coordinates value, ShapeCircle circle) {
        Coordinates scaled = new Coordinates(value.getX(), value.getY() * -1).addCoordinates(center.addCoordinates(offset));

        Circle c = new Circle(scaled.getX() * this.zoomLevel, scaled.getY() * this.zoomLevel,
                circle.getRadius() * this.zoomLevel, javafx.scene.paint.Color.RED);

        loggerList.add("Added a circle");

        contentGroup.getChildren().add(c);
    }
}
