package org.amalgam.client.mainmenu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.client.MainController;

import java.util.Optional;

public class MainMenuController {
    //private variables
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
    private MainController mainController;

    /**
     * Sets the Main Controller.
     *
     * @param mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

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
    public void handlePlay(){
        // insert main controller statement for opening play panel
    }
   @FXML
   public void handleProfile(){
       if (mainController != null) {
           mainController.loadProfileView();
       } else {
           System.out.println("MainController is not set.");
       }
   }
   @FXML
   public void handleLeaderboards(){
        // handle to load leaderboards view
       mainController.loadLeaderboardsView();
   }
   @FXML
   public void handleHistory(){
       if (mainController != null) {
           mainController.loadMatchHistoryView();
       } else {
           System.out.println("MainController is not set.");
       }
   }
   @FXML
   public void handleExit(){
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Exit Confirmation");
       alert.setHeaderText(null);
       alert.setContentText("Are you sure you want to exit the application?");

       Optional<ButtonType> result = alert.showAndWait();
       if (result.isPresent() && result.get() == ButtonType.OK) {
           // Exit the application
           Platform.exit();
       }
    }

    @FXML
    public void handleHowToPlay(){
        if (mainController != null){
            mainController.loadHowToPlayView();
        }else {
            System.out.println("Main controller is not set.");
        }
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
