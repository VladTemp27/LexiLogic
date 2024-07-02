package org.amalgam.client.mainmenu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;
import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.backend.microservices.client.LogoutRequest;
import org.amalgam.client.MainController;
import org.amalgam.client.UIPathResolver;
import org.amalgam.client.login.LoginController;
import org.amalgam.client.profile.ProfileModel;
import sun.java2d.cmm.Profile;

import java.util.Optional;

public class MainMenuController {
    @FXML
    private AnchorPane mainMenuPane;
    @FXML
    private Button playButton;
    @FXML
    private Button leaderboardsButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button howToPlayButton;
    private MainMenuModel mainMenuModel = new MainMenuModel(MainController.orbConnection, LoginController.playerCallback);

    /**
     * Adds hover effect to the given button.
     *
     * @param button The button to add hover effect to.
     */
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#FFF8D6, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #FFF8D6;"));
    }

    /**
     * Shows an alert to a user if there is an error.
     *
     * @param message
     */
    public void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handlePlay(){
        MainController.changeScreen(UIPathResolver.loading_path);
    }

   @FXML
   public void handleProfile(){
       MainController.changeScreen(UIPathResolver.profile_path);
   }
   @FXML
   public void handleLeaderboards(){
        MainController.changeScreen(UIPathResolver.leaderboard_path);
   }
   @FXML
   public void handleHistory(){
       MainController.changeScreen(UIPathResolver.match_history_path);
   }
   @FXML
   public void handleExit(){
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Exit Confirmation");
       alert.setHeaderText(null);
       alert.setContentText("Are you sure you want to exit the application?");

       Optional<ButtonType> result = alert.showAndWait();
       if (result.isPresent() && result.get() == ButtonType.OK) {
           mainMenuModel.logout();
           Platform.exit();
       }
    }

    @FXML
    public void handleHowToPlay(){
        MainController.changeScreen(UIPathResolver.how_to_play_path);
    }
    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        addHoverEffect(profileButton);
        addHoverEffect(leaderboardsButton);
        addHoverEffect(historyButton);
        addHoverEffect(exitButton);
        addHoverEffect(playButton);
        addHoverEffect(howToPlayButton);
        playButton.setOnAction(event -> handlePlay());
        profileButton.setOnAction(event -> handleProfile());
        leaderboardsButton.setOnAction(event -> handleLeaderboards());
        historyButton.setOnAction(event -> handleHistory());
        exitButton.setOnAction(event -> handleExit());
        howToPlayButton.setOnAction(event -> handleHowToPlay());
    }
}
