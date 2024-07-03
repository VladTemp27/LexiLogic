package org.amalgam.client.profile;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.client.MainController;
import org.amalgam.client.UIPathResolver;
import org.amalgam.client.login.LoginController;

public class ProfileChangePassController {
// private variables
    @FXML
    private AnchorPane changepassPane;
    @FXML
    private Button backButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField oldPasswordField;
    @FXML
    private TextField newPasswordField;
    @FXML
    private Label usernameLabel;
    public ProfileChangePassModel profileChangePassModel = new ProfileChangePassModel(MainController.orbConnection,
            LoginController.playerCallback);

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

    private void addHoverEffect(Button button){
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive (#9CA16F, -10%);"));
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

    private void showSuccess(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleSave(){
        String oldPassword = oldPasswordField.getText();
        if (!oldPassword.equals(LoginController.password)){
            showAlert("Old password do not match, please try again");
        } else {
            showSuccess("Password has been changed");
            profileChangePassModel.changePassword(newPasswordField.getText());
            oldPasswordField.clear();
            newPasswordField.clear();
        }
        // handles the save button when the username is changed
    }
    @FXML
    public void handleBack(){
        MainController.changeScreen(UIPathResolver.profile_path);
    }
    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        usernameLabel.setText(LoginController.username);
        addHoverEffect(saveButton);
        addHoverEffectImage(backButton);
        saveButton.setOnAction(event -> handleSave());
        backButton.setOnAction(event -> handleBack());
    }
}
