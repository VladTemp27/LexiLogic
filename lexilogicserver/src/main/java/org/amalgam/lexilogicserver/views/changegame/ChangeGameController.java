package org.amalgam.lexilogicserver.views.changegame;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.amalgam.lexilogicserver.ServerController;

import java.awt.*;

public class ChangeGameController {
    @FXML
    private AnchorPane changeGamePane;
    @FXML
    private TextField changeQueueTextfield;
    @FXML
    private TextField changeGameTextfield;
    @FXML
    private Button saveButton;
    @FXML
    private Button backButton;
    private ServerController serverController;

    /**
     * Sets the Main Controller.
     *
     * @param serverController
     */
    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    /**
     * Adds hover effect to the given button.
     *
     * @param button The button to add hover effect to.
     */
    private void addHoverEffect(javafx.scene.control.Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#9CA16F, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #9CA16F;"));
    }

    /**
     * Shows an alert to a user if there is an error.
     *
     * @param message
     */
    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the save button
     */
    @FXML
    public void handleSaveButton(){
        if(serverController !=null){
            serverController.loadChangeGame();//change to saving option once microservice is ready
        } else {
            System.out.println("Server controller is not set.");
        }
    }
    /**
     * Handles the back button
     */
    @FXML
    public void handleBackButton(){
        if(serverController !=null){
            serverController.loadServerMainMenu();
        } else {
            System.out.println("Server controller is not set.");
        }
    }
    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        addHoverEffect(saveButton);
        addHoverEffect(backButton);
        saveButton.setOnAction(event -> handleSaveButton());
        backButton.setOnAction(event -> handleBackButton());

    }
}
