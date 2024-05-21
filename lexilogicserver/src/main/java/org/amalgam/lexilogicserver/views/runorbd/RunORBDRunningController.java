package org.amalgam.lexilogicserver.views.runorbd;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.amalgam.lexilogicserver.ServerController;

import java.util.concurrent.TimeUnit;

public class RunORBDRunningController {
    // private variables
    @FXML
    private AnchorPane runORBDRunningPane;
    @FXML
    private Button stopORBDButton;
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
    private void addStopHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#E42323, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #E42323;"));
    }

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

    @FXML
    public void handleStopORBDButton() {
        if (RunORBDController.process != null) {
            RunORBDController.process.destroyForcibly();
            try {
                RunORBDController.process.waitFor();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            RunORBDController.process = null;
            showSuccess("ORB Successfully Terminated");
            if (serverController != null) {
                serverController.loadRunORBD();
            } else {
                System.out.println("ServerController is not set.");
            }
        } else {
            showAlert("No ORB to terminate");
        }
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleBackButton() {
        if (serverController != null) {
            serverController.loadServerMainMenu();
        } else {
            System.out.println("Server controller is not set.");
        }
    }

    @FXML
    public void initialize() {
        addStopHoverEffect(stopORBDButton);
        addHoverEffectImage(backButton);
        stopORBDButton.setOnAction(event -> handleStopORBDButton());
        backButton.setOnAction(event -> handleBackButton());

    }
}
