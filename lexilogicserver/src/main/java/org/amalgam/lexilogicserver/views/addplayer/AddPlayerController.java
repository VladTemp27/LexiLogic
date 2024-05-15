package org.amalgam.lexilogicserver.views.addplayer;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.amalgam.lexilogicserver.ServerController;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.Player;

import java.util.ArrayList;
import java.util.List;


public class AddPlayerController {

    @FXML
    private AnchorPane addPlayerPane;

    @FXML
    private Button addPlayerButton;

    @FXML
    private TextField usernameTextfield;
    @FXML
    private Button backButton;

    private List<Player> players;

    private ServerController serverController;

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    private void addHoverEffect(javafx.scene.control.Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#9CA16F, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #9CA16F;"));
    }

    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleAddPlayer(){
        if(serverController != null){
            String username = usernameTextfield.getText();
            if (!username.isEmpty()) {
                // TODO: Connect to microservice for adding player (server)
            } else {
                showAlert("Username cannot be empty.");
            }
        } else {
            System.out.println("Server controller is not set.");
        }
    }

    @FXML
    public void handleBackButton(){
        if(serverController !=null){
            serverController.loadServerMainMenu();
        }else {
            System.out.println("Server controller is not set.");
        }
    }

    @FXML
    public void initialize() {
        addHoverEffect(addPlayerButton);
        addHoverEffect(backButton);
        addPlayerButton.setOnAction(event -> handleAddPlayer());
        backButton.setOnAction(event -> handleBackButton());
    }
}
