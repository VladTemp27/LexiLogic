package org.amalgam.lexilogicserver.views.servermainmenu;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.amalgam.lexilogicserver.ServerController;

public class ServerMainMenuController {
    //private variables
    @FXML
    private AnchorPane serverMainMenuPane;
    @FXML
    private Button addPlayerButton;
    @FXML
    private Button changeGameButton;
    @FXML
    private Button runORBDButton;
    @FXML
    private Button runServerButton;
    @FXML
    private Button accountDeletionButton;
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
    private void addRunHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#9CA16F, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #9CA16F;"));
    }

    private void addHoverEffect(Button button){
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

    /**
     * Handles the add player button
     */
    @FXML
    public void handleAddPlayerButton(){
        if (serverController != null) {
            serverController.loadAddPlayer();
        }else {
            System.out.println("Server controller is not set.");
        }
    }
    /**
     * Handles the change game option button
     */
    @FXML
    public void handleChangeGameButton(){
        if (serverController !=null){
            serverController.loadChangeGame();
        }else{
            System.out.println("Server controller is not set.");
        }
    }
    /**
     * Handles the account deletion button
     */
    @FXML
    public void handleAccountDeletionButton(){
        if(serverController !=null){
            serverController.loadAccountDeletion();
        }else {
            System.out.println("Server controller is not set.");
        }
    }
    /**
     * Handles the run ORBD Button
     */
    @FXML
    public void handleRunORBDButton(){
        if(serverController !=null){
            serverController.loadRunORBD();
        }else {
            System.out.println("Server Controller is not set.");
        }
    }
    /**
     * Handles the runServer Button
     */
    @FXML
    public void handleRunServerButton(){
        if(serverController !=null){
            serverController.loadRunServer();
        }else {
            System.out.println("Server controller is not set");
        }
    }
    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        addHoverEffect(addPlayerButton);
        addHoverEffect(changeGameButton);
        addHoverEffect(runORBDButton);
        addHoverEffect(accountDeletionButton);
        addRunHoverEffect(runServerButton);
        addPlayerButton.setOnAction(event -> handleAddPlayerButton());
        changeGameButton.setOnAction(event -> handleChangeGameButton());
        runORBDButton.setOnAction(event -> handleRunORBDButton());
        runServerButton.setOnAction(event -> handleRunServerButton());
        accountDeletionButton.setOnAction(event -> handleAccountDeletionButton());

    }
}
