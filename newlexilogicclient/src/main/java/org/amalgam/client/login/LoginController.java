package org.amalgam.client.login;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.PlayerCallbackImpl;
import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.UIControllers.PlayerCallbackHelper;
import org.amalgam.client.MainController;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import sun.security.tools.keytool.Main;

public class LoginController {

    // Private Variables
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
    private MainController mainController;
    private LoginModel loginModel = new LoginModel(MainController.orbConnection, null);
    public static PlayerCallback playerCallback;
    public static String username;
    public static String password;

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

    private boolean loginAuthentication(String username, String password) {
        PlayerCallbackImpl playerCallbackImpl = new PlayerCallbackImpl();
        playerCallbackImpl.username(username);
        LoginController.playerCallback = null;
        try {
            playerCallback = PlayerCallbackHelper.narrow(MainController.orbConnection.getPOA().servant_to_reference(playerCallbackImpl));
        } catch (ServantNotActive | WrongPolicy e) {
            throw new RuntimeException(e);
        }
        loginModel.setPlayerCallback(playerCallback);
        return loginModel.login(password);
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

        if (isLoggedIn) {
            mainController.loadMainMenuView();
        } else {
            showAlert("Invalid username or password");
        }
    }
}