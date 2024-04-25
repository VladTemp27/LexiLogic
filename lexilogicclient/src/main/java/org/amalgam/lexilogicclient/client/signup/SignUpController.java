package org.amalgam.lexilogicclient.client.signup;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SignUpController {

    // Private Variables
    @FXML
    private AnchorPane signUpPane;
    @FXML
    private Button signInButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#D9E0A2, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #D9E0A2;"));
    }

    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        addHoverEffect(signInButton);
    }
}
