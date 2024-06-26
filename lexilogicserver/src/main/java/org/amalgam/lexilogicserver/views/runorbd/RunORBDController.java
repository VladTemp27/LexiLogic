package org.amalgam.lexilogicserver.views.runorbd;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.amalgam.lexilogicserver.ServerController;
import org.amalgam.lexilogicserver.model.microservices.daemonHandler.ORBDException;
import org.amalgam.lexilogicserver.model.microservices.daemonHandler.ORBDOperationCallback;
import org.amalgam.lexilogicserver.model.microservices.daemonHandler.ORBDRunner;
import org.amalgam.lexilogicserver.views.servermainmenu.ServerMainMenuController;

public class RunORBDController implements ORBDOperationCallback {

    // Private Variables
    @FXML
    private AnchorPane runORBDPane;
    @FXML
    private TextField hostNameField;
    @FXML
    private TextField portField;
    @FXML
    private Button runORBDButton;
    @FXML
    private Button backButton;
    private ServerController serverController;
    public static Process process;

    public static ExecutorService executorService = Executors.newSingleThreadExecutor();

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
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#9CA16F, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #9CA16F;"));
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
     * Shows an alert to a user if there is an error.
     *
     * @param message
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Validates the hostname and port.
     *
     * @param hostname The hostname to validate.
     * @param port     The port to validate.
     * @return True if hostname and port are valid, false otherwise.
     */
    @Deprecated
    private boolean isValidHostAndPort(String hostname, int port) { //TODO: Checking of Validity of Host and Port
        if (hostname == null || hostname.isEmpty() || port < 1 || port > 65535) {
            showAlert("Invalid Hostname or Port. Please check your input.");
            return false;
        }

        Socket socket = new Socket();
        int timeout = 5000; // 5 seconds timeout for connection

        try {
            socket.connect(new InetSocketAddress(hostname, port), timeout);
            socket.close();
            return true; // Connection successful
        } catch (SocketTimeoutException e) {
            showAlert("Connection timed out. The host may be down or unreachable.");
        } catch (Exception e) {
            showAlert("Invalid Hostname or Port. Please check your input.");
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                // Ignore
            }
        }
        return false; // Connection failed
    }

    /**
     * Handles the Run ORBD Button.
     */
    @FXML
    public void handleRunORBDButton() { //TODO: Checking of Validity of Host and Port
        String hostname = hostNameField.getText().trim();
        String portText = portField.getText().trim();

        ServerController.hostname = hostname;
        ServerController.port = Integer.parseInt(portText);

        // Check if hostname or port is empty
        if (hostname.isEmpty() || portText.isEmpty()) {
            showAlert("Please enter both hostname and port.");
            return;
        }

        try {
            int portNo = Integer.parseInt(portText);
            ServerController.ORBExitCode = executorService.submit(new ORBDRunner(this, portNo, hostname));
            showSuccess("ORB Daemon successfully run.");
            ServerController.isDaemonRunning =true;

            if (serverController != null) {
                serverController.loadRunORBDRunningView();
            } else {
                System.out.println("ServerController is not set.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid port number. Please enter a valid port number.");
        } catch (Exception e) {
            showAlert("Failed to run ORB Daemon: " + e.getMessage());
        }
    }

    @FXML
    public void handleBackButton() {
        if (serverController != null) {
            serverController.loadServerMainMenu();
        } else {
            System.out.println("Server controller is not set.");
        }
    }

    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        addHoverEffect(runORBDButton);
        addHoverEffectImage(backButton);
        runORBDButton.setOnAction(event -> handleRunORBDButton());
        backButton.setOnAction(event -> handleBackButton());
    }

    @Override
    public void notifyOrbExit() throws ORBDException {
        ServerController.isDaemonRunning = false;
    }

    @Override
    public void setProcessObject(Process process) {
        System.out.println(process);
        RunORBDController.process = process;
    }
}
