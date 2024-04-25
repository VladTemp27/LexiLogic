package org.amalgam.lexilogicclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.amalgam.lexilogicclient.client.login.LoginController;
import org.amalgam.lexilogicclient.client.matchhistory.MatchHistoryController;
import org.amalgam.lexilogicclient.client.signup.SignUpController;

import java.io.IOException;
import java.io.InputStream;

public class MainController {
    public Stage stage;

    /**
     * Controller and Panel Variables
     */
    static LoginController loginController;
    static AnchorPane loginPanel;

    static SignUpController signUpController;
    static AnchorPane signUpPanel;

    static MatchHistoryController matchHistoryController;
    static AnchorPane matchHistoryPanel;

    /**
     * Getters and Setters of Controllers and Panels
     */
    public void setStage(Stage stage) { this.stage = stage;}
    public LoginController getLoginController() { return loginController; }
    public SignUpController getSignUpController() { return signUpController; }
    public MatchHistoryController getMatchHistoryController() { return matchHistoryController; }

    /**
     * Loads and displays the login view.
     */
    public void loadLoginView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/lexilogicclient/client/views/login/login-view.fxml"));
            AnchorPane loginPane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("icons/Logo.png");
            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(loginPane);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            LoginController loginController = fxmlLoader.getController();
            loginController.setMainController(this);
            loginController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the signup view.
     */
    public void loadSignUpView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/lexilogicclient/client/views/signup/signup-view.fxml"));
            AnchorPane signuppane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("icons/Logo.png");

            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(signuppane);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            SignUpController signUpController = fxmlLoader.getController();
            signUpController.setMainController(this);
            signUpController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the matchHistory view.
     */
    public void loadMatchHistoryView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/lexilogicclient/client/views/matchhistory/matchhistory-view.fxml"));
            AnchorPane matchHistoryPane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("icons/Logo.png");

            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(matchHistoryPane);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            MatchHistoryController matchHistoryController = fxmlLoader.getController();
            matchHistoryController.setMainController(this);
            matchHistoryController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}