package org.amalgam.lexilogicserver.views.runserver;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import org.amalgam.lexilogicserver.ServerController;

public class RunServerController {

    // Private Variables
    @FXML
    private AnchorPane runServerPane;
    @FXML
    private Label serverUpdateLabel;
    @FXML
    private ProgressIndicator progressIndicator;
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

    public void ifServerIsRunning() {
        if (serverController != null) {
            serverController.loadRunServerRunning();
        } else {
            System.out.println("ServerController is not set.");
        }
    }

    public void showServerRunning() {
        //TODO: Text should show all running things for the server.
        serverUpdateLabel.setText("Server is Running...");
    }

     /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        ifServerIsRunning();
        showServerRunning();
    }
}
