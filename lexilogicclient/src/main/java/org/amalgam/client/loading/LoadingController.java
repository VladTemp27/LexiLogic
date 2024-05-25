package org.amalgam.client.loading;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.amalgam.UpdateDispatcher;
import org.amalgam.client.MainController;
import javafx.scene.image.ImageView;
import org.amalgam.client.UIPathResolver;
import org.amalgam.client.login.LoginController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadingController implements UpdateDispatcher {
    // Private Variables
    public static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private String statusBody = "";
    @FXML
    private AnchorPane loadingPane;
    @FXML
    private ImageView loadingLogImage;
    public LoadingModel loadingModel = new LoadingModel(MainController.orbConnection, LoginController.playerCallback);

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

    @FXML
    public void initialize() {
        animateLog(); // animation for the loading log

        /**
         * initialize the interface of the callback of a player
         */
        LoginController.playerCallbackImpl.setControllerInterface(this);

        Task<Void> t1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                findMatch();
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                Platform.runLater(() -> {
                    checkMatch();
                });
            }
        };

        executorService.submit(t1);

    }

    public void findMatch()  {
        System.out.println("FINDING MATCH...");
        String response = loadingModel.matchMake();
        System.out.println("LOADING RESPONSE " + response);
        JsonElement rootElement = JsonParser.parseString(response);
        JsonObject jsonObject = rootElement.getAsJsonObject();
        statusBody = jsonObject.get("status").getAsString();
    }

    public void checkMatch() {
        if (statusBody.isEmpty()) {
        return;
        }
        if(statusBody.equals("timeout")){
            MainController.changeScreen(UIPathResolver.main_menu_path);
        } else {
            System.out.println("MATCH FOUND...");
            MainController.changeScreen(UIPathResolver.game_path);
        }
    }

    @Override
    public void update(String jsonString) {
        System.out.println("LOADING data"+ jsonString);
    }
}
