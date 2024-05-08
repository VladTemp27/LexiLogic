package org.amalgam.client;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        MainController mainController = new MainController();
        mainController.setStage(primaryStage);
        mainController.loadLoginView();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
