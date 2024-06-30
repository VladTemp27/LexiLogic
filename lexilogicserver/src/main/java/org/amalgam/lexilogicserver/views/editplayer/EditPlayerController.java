package org.amalgam.lexilogicserver.views.editplayer;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.amalgam.lexilogicserver.ServerController;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.Player;
import org.amalgam.lexilogicserver.views.playermanagement.PlayerManagementModel;

import java.util.Objects;
import java.util.Optional;

public class EditPlayerController {

    @FXML
    private AnchorPane editPlayerPane;
    @FXML
    private Button saveButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button backButton;
    //    private List<Player> players;
    private ServerController serverController;

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
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

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#9CA16F, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #9CA16F;"));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleSaveButton() {
        if (serverController != null) {
            int playerID = PlayerManagementModel.getSelectedPlayer().getUserID();
            String username = "";
            String password = "";
            if (!Objects.equals(usernameTextField.getText(), "") && Objects.equals(passwordTextField.getText(), "")) {
                username = usernameTextField.getText();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Edit Player");
                alert.setHeaderText("Are you sure you want to edit " + PlayerManagementModel.getSelectedPlayer().getUsername() + "'s username?");
                alert.setContentText("This action cannot be undone.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    PlayerManagementModel.changeUsername(playerID, username);
                    serverController.loadPlayerManagement();
                }
            }
            if (Objects.equals(usernameTextField.getText(), "") && !Objects.equals(passwordTextField.getText(), "")) {
                password = passwordTextField.getText();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Edit Player");
                alert.setHeaderText("Are you sure you want to change " + PlayerManagementModel.getSelectedPlayer().getUsername() + "'s password?");
                alert.setContentText("This action cannot be undone.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    PlayerManagementModel.changePassword(playerID, password);
                    serverController.loadPlayerManagement();
                }
            }
            if (!Objects.equals(usernameTextField.getText(), "") && !Objects.equals(passwordTextField.getText(), "")) {
                username = usernameTextField.getText();
                password = passwordTextField.getText();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Edit Player");
                alert.setHeaderText("Are you sure you want to change " + PlayerManagementModel.getSelectedPlayer().getUsername() +
                        "'s username and " +
                        "password?");
                alert.setContentText("This action cannot be undone.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    PlayerManagementModel.changeUsername(playerID, username);
                    PlayerManagementModel.changePassword(playerID, password);
                    serverController.loadPlayerManagement();
                }
            }
        }
    }

    @FXML
    public void handleBackButton() {
        if (serverController != null) {
            serverController.loadPlayerManagement();
        } else {
            System.out.println("Server controller is not set.");
        }
    }

    @FXML
    public void initialize() {
            addHoverEffect(saveButton);
            addHoverEffectImage(backButton);
            backButton.setOnAction(event -> handleBackButton());
            saveButton.setOnAction(event -> handleSaveButton());
    }
}
