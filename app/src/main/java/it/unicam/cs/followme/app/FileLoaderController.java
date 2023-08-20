package it.unicam.cs.followme.app;

import it.unicam.cs.followme.utilities.FollowMeParserException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * This controller is responsible for the input of the files and robots.
 */
public class FileLoaderController {

    /**
     * Environment file.
     */
    private File loadEnv;

    /**
     * Program File.
     */
    private File loadProgram;

    /**
     * Text field where user inputs number of robots to spawn.
     */
    @FXML
    private TextField robotField;

    /**
     * Calls a method to load the environment file with a title.
     * @param actionEvent
     */
    @FXML
    public void loadEnv(ActionEvent actionEvent) {
        loadEnv = loadFile("Choose an environment file", actionEvent);
    }

    /**
     * Calls a method to load the program file with a title.
     * @param actionEvent
     */
    @FXML
    public void LoadProgram(ActionEvent actionEvent) {
        loadProgram = loadFile("Choose a program file", actionEvent);
    }

    /**
     * Loads a file with a FileChooser.
     * @param textLoader Title of the file chooser.
     * @param actionEvent
     * @return the file chosen by the user.
     */
    private File loadFile(String textLoader, ActionEvent actionEvent) {
        FileChooser choose = new FileChooser();
        choose.setTitle(textLoader);
        choose.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("all files", "*.*"));

        String userDirectoryString = Objects.requireNonNull(actionEvent.getSource().getClass().getClassLoader()
                .getResource("")).getPath() + "..//..//..//..//src//main//resources//";

        File userDirectory = new File(userDirectoryString);
        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        choose.setInitialDirectory(userDirectory);
        return choose.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
    }

    /**
     * Loads the simulation stage with the user input.
     * @param actionEvent
     * @throws IOException
     * @throws FollowMeParserException
     */
    public void run(ActionEvent actionEvent) throws IOException, FollowMeParserException {
        //{

        if(this.loadEnv == null || this.loadProgram == null) {
            return;
        }

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("scene.fxml"));
            Parent root = loader.load();
            SceneController sceneController = loader.getController();

            if(this.robotField.getText().isEmpty()) sceneController.parseFiles(loadEnv, loadProgram, 1);
            else sceneController.parseFiles(loadEnv, loadProgram, Integer.parseInt(robotField.getText()));

            Stage stage = (Stage) (((Node)actionEvent.getSource()).getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

        /*}
        catch (Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(e.toString());
            a.show();
        }*/
    }
}
