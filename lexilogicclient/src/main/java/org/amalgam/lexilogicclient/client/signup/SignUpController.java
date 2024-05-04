package org.amalgam.lexilogicclient.client.signup;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.amalgam.lexilogicclient.MainController;

public class SignUpController {

    // Private Variables
    @FXML
    private AnchorPane signUpPane;
    @FXML
    private Button signInButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

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

    // TODO: Uploading the user to the DataBase & Error Checking
    // Test Authentication
    private boolean signUpAuthentication(String username, String password) {
        return true;
    }

    /**
     * Shows the login view when pressed.
     */
    @FXML
    public void handleLogin() {
        if (mainController != null) {
            mainController.loadLoginView();
        } else {
            System.out.println("MainController is not set.");
        }
    }

    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        addHoverEffect(signInButton);
        addHoverEffect(loginButton);
        loginButton.setOnAction(event -> handleLogin());
        signInButton.setOnAction(event -> onSignUp());
    }

    /**
     * Handles the Login of the user.
     *
     *
     */
    @FXML
    public void onSignUp() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isSignedUp = signUpAuthentication(username, password);

        if (isSignedUp) {
            mainController.loadLoginView();
        } else {
            showAlert("Failed to sign up. Please try again.");
        }
    }

    /**
     * Gets the objects used.
     * This method returns a string indicating the type of objects used by the controller.
     *
     * @return A string representing the objects used.
     */
    //TODO: @Override
    private void getObjectsUsed() {
        //TODO: Return Value
        //return "user";
    }

    /**
     * Fetches and updates data remotely.
     * This method is called to update the data displayed in the UI.
     *
     */
    //TODO: @Override
    private void fetchAndUpdate() {
        //TODO: Fetching of Data
    }
}
