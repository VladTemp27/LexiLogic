package org.amalgam.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.amalgam.client.leaderboards.LeaderboardsController;
import org.amalgam.client.leaderboards.LeaderboardsPanel;
import org.amalgam.client.loading.LoadingController;
import org.amalgam.client.login.LoginController;
import org.amalgam.client.mainmenu.MainMenuController;
import org.amalgam.client.matchhistory.MatchHistoryController;
import org.amalgam.client.profile.ProfileChangePassController;
import org.amalgam.client.profile.ProfileChangeUsernameController;
import org.amalgam.client.profile.ProfileController;
import org.amalgam.client.signup.SignUpController;

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

    static ProfileController profileController;
    static AnchorPane profilePane;

    static MainMenuController mainMenuController;
    static AnchorPane mainMenuPane;

    static ProfileChangeUsernameController profileChangeUsernameController;
    static AnchorPane changeUsernamePane;

    static ProfileChangePassController profileChangePassController;
    static AnchorPane changePassPane;

    static LoadingController loadingController;
    static AnchorPane loadingPane;

    static LeaderboardsController leaderboardsController;
    static AnchorPane leaderboardsPane;

    /**
     * Getters and Setters of Controllers and Panels
     */
    public void setStage(Stage stage) { this.stage = stage;}
    public LoginController getLoginController() { return loginController; }
    public SignUpController getSignUpController() { return signUpController; }
    public MatchHistoryController getMatchHistoryController() { return matchHistoryController; }
    public ProfileController getProfileController(){ return profileController;}
    public ProfileChangeUsernameController getProfileChangeUsernameController(){ return profileChangeUsernameController;}
    public ProfileChangePassController getProfileChangePassController(){return profileChangePassController;}
    public MainMenuController getMainMenuController(){return mainMenuController;}
    public LoadingController getLoadingController(){return loadingController;}
    public LeaderboardsController getLeaderboardsController(){return leaderboardsController;}

    /**
     * Loads and displays the login view.
     */
    public void loadLoginView() {
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/client/views/login/login-view.fxml"));
            AnchorPane loginPane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");
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
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/client/views/signup/signup-view.fxml"));
            AnchorPane signuppane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");

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
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/client/views/matchhistory/matchhistory-view.fxml"));
            AnchorPane matchHistoryPane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");

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

    /**
     * Loads and displays the profile view.
     */
    public void loadProfileView(){
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/client/views/profile/profile-view.fxml"));
            AnchorPane profilePane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");

            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(profilePane);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            ProfileController profileController = fxmlLoader.getController();
            profileController.setMainController(this);
            profileController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the profileChangeUsername view.
     */
    public void loadProfileChangeUsernameView(){
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/client/views/profile/profileChangeUsername-view.fxml"));
            AnchorPane changeUsernamePane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");

            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(changeUsernamePane);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            ProfileChangeUsernameController profileChangeUsernameController = fxmlLoader.getController();
            profileChangeUsernameController.setMainController(this);
            profileChangeUsernameController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the profileChangePassword view.
     */
    public void loadProfileChangePasswordView(){
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/client/views/profile/profileChangePass-view.fxml"));
            AnchorPane changepassPane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");

            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(changepassPane);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            ProfileChangePassController profileChangePassController = fxmlLoader.getController();
            profileChangePassController.setMainController(this);
            profileChangePassController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the mainMenu view.
     */
    public void loadMainMenuView(){
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/client/views/mainmenu/mainmenu-view.fxml"));
            AnchorPane mainmenuPane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");

            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(mainmenuPane);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            MainMenuController mainMenuController = fxmlLoader.getController();
            mainMenuController.setMainController(this);
            mainMenuController.initialize();
            mainMenuController.authenticate("admin","admin");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the game view.
     */
    public void loadGameView(){
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/client/views/game/game-view.fxml"));
            AnchorPane gamePane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");

            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(gamePane);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            MainMenuController mainMenuController = fxmlLoader.getController();
            mainMenuController.setMainController(this);
            mainMenuController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
    Load and display the loading view
     */
    public void loadLoadingView(){
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/client/views/loading/loading-view.fxml"));
            AnchorPane loadingPane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");

            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(loadingPane);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            LeaderboardsController leaderboardsController1 = fxmlLoader.getController();
            loadingController.setMainController(this);
            loadingController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load and display leaderboards view
     */
    public void loadLeaderboardsView(){
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/client/views/leaderboards/leaderboards-view.fxml"));
            AnchorPane leaderboardsPane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");

            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(leaderboardsPane);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            LeaderboardsController leaderboardsController = fxmlLoader.getController();
            leaderboardsController.setMainController(this);
            leaderboardsController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}