package org.amalgam.client.profile;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.client.MainController;

public class ProfileController {

    // private variables

    @FXML
    private AnchorPane profilePane;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button backButton;
    @FXML
    private Button editUsernameButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label scoreLabel;
    private MainController mainController;

    /**
     * Sets the Main Controller.
     *
     * @param mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Adds hover effect to the given button.
     *
     * @param button The button to add hover effect to.
     */
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#D9E0A2, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #D9E0A2;"));
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
     * Gets the objects used.
     * This method returns a string indicating the type of objects used by the controller.
     *
     * @return A string representing the objects used.
     */
    public void setObjectsUser(String objects) throws InvalidRequestException {

    }

    /**
     * Fetches and updates data remotely.
     * This method is called to update the data displayed in the UI.
     *
     */
    public void fetchAndUpdate(String jsonString, String dataType) throws InvalidRequestException {

    }

    /**
     * Shows the change username view when pressed.
     */
    @FXML
    public void handleChangePassword() {
        if (mainController != null) {
            mainController.loadProfileChangePasswordView();
        } else {
            System.out.println("MainController is not set.");
        }
    }

    /**
     * Shows the edit username view when pressed
     */
    @FXML
    public void handleEditUsername(){
        if (mainController != null) {
            mainController.loadProfileChangeUsernameView();
        } else {
            System.out.println("MainController is not set.");
        }
    }

    /**
     * goes back to the main menu panel when pressed
     */
    @FXML
    public void handleBack(){
        if (mainController != null) {
            mainController.loadMainMenuView();
        } else {
            System.out.println("MainController is not set.");
        }
    }

    /**
     * logouts the user when pressed
     */
    @FXML
    public void handleLogout(){
        // insert exception for handling logout (di galing sa gpt to pramis)
    }

    /**
     * delete the user account when pressed
     */
    @FXML
    public void handleDelete(){
        // insert exception for handling deletion of user account (di galing sa gpt din hehe)
    }

    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        addHoverEffect(changePasswordButton);
        addHoverEffect(editUsernameButton);
        addHoverEffect(backButton);
        addHoverEffect(deleteButton);
        addHoverEffect(logoutButton);
        changePasswordButton.setOnAction(event -> handleChangePassword());
        editUsernameButton.setOnAction(event -> handleEditUsername());
        backButton.setOnAction(event -> handleBack());
    }
}
