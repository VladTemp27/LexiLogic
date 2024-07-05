package org.amalgam.client.login;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.amalgam.PlayerCallbackImpl;
import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.UIControllers.PlayerCallbackHelper;
import org.amalgam.Utils.Exceptions.InvalidCredentialsException;
import org.amalgam.Utils.Exceptions.UserExistenceException;
import org.amalgam.client.MainController;
import org.amalgam.client.UIPathResolver;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

public class LoginController {
    @FXML
    private AnchorPane loginPane;
    @FXML
    private Button loginButton;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    private LoginModel loginModel = new LoginModel(MainController.orbConnection, null);
    public static PlayerCallback playerCallback;
    public static PlayerCallbackImpl playerCallbackImpl;
    public static String username;
    public static String password;

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

    private boolean loginAuthentication(String username, String password) {
        playerCallbackImpl = new PlayerCallbackImpl();
        playerCallbackImpl.username(username);
        playerCallback = null;
        try {
            playerCallback = PlayerCallbackHelper.narrow(MainController.orbConnection.getPOA().servant_to_reference(playerCallbackImpl));
        } catch (ServantNotActive | WrongPolicy e) {
            throw new RuntimeException(e);
        }
        loginModel.setPlayerCallback(playerCallback);
        try {
            return loginModel.login(password);
        } catch (RuntimeException e) {
            if (e.getCause() instanceof InvalidCredentialsException) {
                showAlert("Invalid username or password");
                return false;
            } else if (e.getCause() instanceof UserExistenceException) {
                showAlert("User does not exist");
                return false;
            }
            throw e;
        }
    }

    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        addHoverEffect(loginButton);
        loginButton.setOnAction(event -> onLogin());
    }

    /**
     * Handles the Login of the user.
     *
     *
     */
    @FXML
    public void onLogin() {
        username = usernameField.getText();
        password = passwordField.getText();

        boolean isLoggedIn = loginAuthentication(username, password);
        System.out.println("TRUE");

        if (isLoggedIn) {
            MainController.changeScreen(UIPathResolver.main_menu_path);
        }
    }

}