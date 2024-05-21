package org.amalgam.client.profile;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.client.MainController;
import org.amalgam.client.login.LoginController;

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
    private MainController mainController;
    private ProfileModel profileModel = new ProfileModel(MainController.orbConnection, LoginController.playerCallback);


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
    private void addHoverEffectImage(Button button) {
        ImageView imageView = (ImageView) button.getGraphic();
        ColorAdjust colorAdjust = new ColorAdjust();

        button.setOnMouseEntered(e -> {
            colorAdjust.setBrightness(-0.3); // Decrease brightness to make it darker
            imageView.setEffect(colorAdjust);
        });

        button.setOnMouseExited(e -> {
            colorAdjust.setBrightness(0); // Reset brightness
            imageView.setEffect(colorAdjust);
        });
    }

    private void addDeleteHoverEffect(Button button){
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#A94949, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #A94949;"));
    }

    private void addChangePasswordHoverEffect(Button button){
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#4b5320, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #4b5320;"));
    }

    private void addLogoutHoverEffect(Button button){
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
        try {
            profileModel.logOut();
            mainController.loadLoginView();
        } catch (Exception e) {
            showAlert("Error during logout: " + e.getMessage());
        }    }

    /**
     * delete the user account when pressed
     */
    @FXML
    public void handleDelete(){
        try {
            profileModel.accountDeletionRequest();
            showAlert("Account deletion request sent successfully.");
        } catch (Exception e) {
            System.out.println(e.getCause());
            showAlert("Error during account deletion: " + e.getMessage());
        }    }

    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        usernameLabel.setText(LoginController.username);
        addChangePasswordHoverEffect(changePasswordButton);
        addDeleteHoverEffect(deleteButton);
        addLogoutHoverEffect(logoutButton);
        addHoverEffectImage(editUsernameButton);
        addHoverEffectImage(backButton);
        changePasswordButton.setOnAction(event -> handleChangePassword());
        editUsernameButton.setOnAction(event -> handleEditUsername());
        backButton.setOnAction(event -> handleBack());
        logoutButton.setOnAction(event -> handleLogout());
        deleteButton.setOnAction(event -> handleDelete());
    }
}
