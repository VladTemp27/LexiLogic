package org.amalgam.lexilogicserver;

import javafx.application.Application;
import javafx.stage.Stage;

public class Server extends Application {
    private ServerController serverController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        serverController = new ServerController();
        serverController.setStage(primaryStage);
        serverController.loadServerMainMenu();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
