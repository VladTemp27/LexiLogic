package org.amalgam.client;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Notes:
 * - @FXML annotated static variables are not initialize when FXML LOADER loads the .fxml/panel because its
 * value must be initialized manually rather than implicitly.
 */
public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainController mainController = new MainController(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
