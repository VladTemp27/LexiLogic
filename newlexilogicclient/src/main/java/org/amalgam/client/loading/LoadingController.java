package org.amalgam.client.loading;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.ControllerInterface;
import org.amalgam.client.MainController;
import javafx.scene.image.ImageView;
import org.amalgam.client.game.GameController;
import org.amalgam.client.login.LoginController;

public class LoadingController implements ControllerInterface {
    // Private Variables
    private String statusBody = "";
    @FXML
    private AnchorPane loadingPane;
    @FXML
    private ImageView loadingLogImage;
    private MainController mainController;
    public LoadingModel loadingModel = new LoadingModel(MainController.orbConnection, LoginController.playerCallback);
    /**
     * Sets the Main Controller.
     *
     * @param mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void animateLog(){
        Platform.runLater(() -> {
            RotateTransition rotateTransition = new RotateTransition();
            rotateTransition.setNode(loadingLogImage);
            rotateTransition.setDuration(Duration.millis(1000));
            rotateTransition.setCycleCount(TranslateTransition.INDEFINITE);
            rotateTransition.setInterpolator(Interpolator.LINEAR);
            rotateTransition.setByAngle(360);
            rotateTransition.setAxis(Rotate.Z_AXIS);
            rotateTransition.play();
        });
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

    @FXML
    public void initialize() {
        animateLog(); // animation for the loading log
    }

    public void findMatch() {
        String response = loadingModel.matchMake();
        System.out.println(response);
        JsonElement rootElement = JsonParser.parseString(response);
        JsonObject jsonObject = rootElement.getAsJsonObject();
        statusBody = jsonObject.get("status").getAsString();
        checkMatch();
    }

    public void checkMatch() {
        Platform.runLater(() -> {
            if (statusBody.isEmpty()) {
            return;
            }
            System.out.println(statusBody);
            if(statusBody.equals("timeout")){
                mainController.loadMainMenuView();
            } else {
                mainController.loadGameView();
            }
        });
    }

    @Override
    public void currentController(String jsonString) {
        mainController.getGameController().updateData(jsonString);
    }
}
