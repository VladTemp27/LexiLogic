package org.amalgam.lexilogicserver.views.runserver;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.amalgam.lexilogicserver.ServerController;
import org.amalgam.lexilogicserver.model.microservices.serverHandler.ORBServer;

import java.util.concurrent.TimeUnit;

import static org.amalgam.lexilogicserver.ServerController.isDaemonRunning;
import static org.amalgam.lexilogicserver.ServerController.isServerRunning;
import static sun.net.www.protocol.http.AuthCacheValue.Type.Server;

public class RunServerRunningController {

    //Private Variables
    @FXML
    private AnchorPane runServerRunningPane;
    @FXML
    private Button stopServerButton;
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

    /**
     * Adds hover effect to the given button.
     *
     * @param button The button to add hover effect to.
     */
    private void addPlayerHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#B07C3B, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #B07C3B;"));
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
    /**
     * Method to stop the server by closing the main stage
     */
    public void stopServer() {
        try {
            ServerController.semaphore.acquire(); // Acquire semaphore before stopping the server
            if (ServerController.serverExecutor != null && !ServerController.serverExecutor.isShutdown()) {
                ServerController.serverExecutor.shutdownNow();
                try {
                    if (!ServerController.serverExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                        ServerController.serverExecutor.shutdownNow();
                    }
                } catch (InterruptedException ie) {
                    ServerController.serverExecutor.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
            isServerRunning = false;

            if (isServerRunning) {
                showAlert("Server failed to shutdown");
            } else if (isDaemonRunning) {
                showSuccess("Server shutdown");
                serverController.loadServerMainMenu();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            showAlert("Failed to acquire semaphore for stopping server");
        } finally {
            ServerController.semaphore.release(); // Release semaphore after stopping the server
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void handleStopServer() {
            stopServer();
    }

    @FXML
    public void handleBackButton(){
        if(serverController !=null){
            serverController.loadServerMainMenu();
        }else {
            System.out.println("Server controller is not set.");
        }
    }

    @FXML
    public void initialize() {
        stopServerButton.setOnAction(event -> handleStopServer());
        backButton.setOnAction(event -> handleBackButton());
        addStopHoverEffect(stopServerButton);
        addHoverEffectImage(backButton);
    }
}
