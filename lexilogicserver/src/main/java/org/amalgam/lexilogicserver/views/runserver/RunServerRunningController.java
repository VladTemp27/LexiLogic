package org.amalgam.lexilogicserver.views.runserver;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.amalgam.lexilogicserver.ServerController;

public class RunServerRunningController {

    //Private Variables
    @FXML
    private AnchorPane runServerRunningPane;
    @FXML
    private Button addPlayerButton;
    @FXML
    private Button stopServerButton;
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
    private void addStopHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#E42323, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #E42323;"));
    }

    /**
     * Adds hover effect to the given button.
     *
     * @param button The button to add hover effect to.
     */
    private void addPlayerHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#B07C3B, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #B07C3B;"));
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

    @FXML
    public void handleAddPlayer() {
        if (serverController != null) {
            serverController.loadAddPlayer();
        } else {
            System.out.println("ServerController is not set.");
        }
    }

    @FXML
    public void handleStopServer() {
        //TODO: Make the server stop when button is clicked.
    }

    @FXML
    public void initialize() {
        addPlayerButton.setOnAction(event -> handleAddPlayer());
        stopServerButton.setOnAction(event -> handleStopServer());
        addPlayerHoverEffect(addPlayerButton);
        addStopHoverEffect(stopServerButton);
    }
}
