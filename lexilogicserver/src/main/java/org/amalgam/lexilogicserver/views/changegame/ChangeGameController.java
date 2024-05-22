package org.amalgam.lexilogicserver.views.changegame;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.amalgam.lexilogicserver.ServerController;


public class ChangeGameController {
    @FXML
    private AnchorPane changeGamePane;
    @FXML
    private TextField changeQueueTextfield;
    @FXML
    private TextField changeGameTextfield;
    @FXML
    private Button saveButton;
    @FXML
    private Button backButton;
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

    /**
     * Shows an alert to a user if there is an error.
     *
     * @param message
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Setting Changed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the save button
     */
    @FXML
    public void handleSaveButton() {
        if (serverController != null) {
            handleChangeQueueTime();
            serverController.loadChangeGame();//change to saving option once microservice is ready
        } else {
            System.out.println("Server controller is not set.");
        }
    }

    /**
     * Handles the back button
     */
    @FXML
    public void handleBackButton() {
        if (serverController != null) {
            serverController.loadServerMainMenu();
        } else {
            System.out.println("Server controller is not set.");
        }
    }

    public void handleChangeQueueTime() {
        if (serverController != null) {
            int newQueueTime = Integer.parseInt(changeQueueTextfield.getText());
            int newGameTime = Integer.parseInt(changeGameTextfield.getText());
            try {
                if (!(null == changeQueueTextfield.getText()) && changeGameTextfield.getText() == null) {
                    ChangeGameModel.changeQueueTime(newQueueTime);
                    showAlert("You have changed the queue time");
                } else if (!(null == changeGameTextfield.getText()) && changeQueueTextfield.getText() == null) {
                    ChangeGameModel.changeGameTime(newGameTime);
                    showAlert("You have changed the game time");
                }else if (!(null == changeGameTextfield.getText()) && !(null == changeQueueTextfield.getText())){
                    ChangeGameModel.changeGameTime(newGameTime);
                    ChangeGameModel.changeQueueTime(newQueueTime);
                    showAlert("You have changed the queue and game time");
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        addHoverEffect(saveButton);
        addHoverEffectImage(backButton);
        saveButton.setOnAction(event -> handleSaveButton());
        backButton.setOnAction(event -> handleBackButton());

    }
}
