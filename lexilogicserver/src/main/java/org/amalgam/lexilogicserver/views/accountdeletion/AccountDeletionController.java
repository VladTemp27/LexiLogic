package org.amalgam.lexilogicserver.views.accountdeletion;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.amalgam.lexilogicserver.ServerController;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.Player;

public class AccountDeletionController {
    //private variables
    @FXML
    private AnchorPane accountDeletionPane;
    @FXML
    private Button saveButton;
    @FXML
    private Button backButton;
    @FXML
    private TableView<String> accountTable;
    @FXML
    private TableColumn<String,String> accountTableColumn;
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
    private void addHoverEffect(Button button) {
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
     * Handles the back button
     */
    @FXML
    public void handleBackButton(){
        if(serverController !=null){
            serverController.loadServerMainMenu();
        }else {
            System.out.println("Server controller is not set.");
        }
    }

    /**
     * Handles the save button
     */
    @FXML
    public void handleSaveButton(){
        if(serverController !=null){
            serverController.loadServerMainMenu();
        } else {
            System.out.println("Server controller is not set.");
        }
    }
    @FXML
    public void initialize(){
        addHoverEffect(saveButton);
        addHoverEffect(backButton);
        saveButton.setOnAction(event -> handleSaveButton());
        backButton.setOnAction(event -> handleBackButton());

    }
}
