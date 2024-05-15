package org.amalgam.client.mainmenu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.client.MainController;

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
    private MainController mainController;
    private MainMenuModel mainMenuModel;


    /**
     * Sets the Main Controller.
     *
     * @param mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void setMainMenuModel(MainMenuModel mainMenuModel) {
        this.mainMenuModel = mainMenuModel;
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

    /**
     * Handle the play button
     */
    @FXML
    public void handlePlay(){
        if (mainMenuModel != null) {
            String result = mainMenuModel.matchMake();
        } else {
            System.out.println("MainMenuModel is not set.");
        }
    }

    /**
     * Handle the profile button
     */
   @FXML
   public void handleProfile(){
       if (mainController != null) {
           mainController.loadProfileView();
       } else {
           System.out.println("MainController is not set.");
       }
   }

    /**
     * Handle the leaderboards button
     */
   @FXML
   public void handleLeaderboards(){
        if(mainController !=null){
            mainController.loadLeaderboardsView();
        }else {
            System.out.println("Server controller is not set.");
        }
   }

    /**
     * Handle the history button
     */
   @FXML
   public void handleHistory(){
       if (mainController != null) {
           mainController.loadMatchHistoryView();
       } else {
           System.out.println("MainController is not set.");
       }
   }

    /**
     * Handle the exit button
     */
   @FXML
   public void handleExit(){
       Dialog<ButtonType> confirmationDialog = new Dialog<>();
       confirmationDialog.setTitle("Confirm Exit");
       confirmationDialog.setHeaderText("Are you sure you want to exit?");
       confirmationDialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);

       confirmationDialog.showAndWait().ifPresent(response -> {
           if (response == ButtonType.YES) {
               Platform.exit();
           }
       });   }
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
        playButton.setOnAction(event -> handlePlay());
        profileButton.setOnAction(event -> handleProfile());
        leaderboardsButton.setOnAction(event -> handleLeaderboards());
        historyButton.setOnAction(event -> handleHistory());
        exitButton.setOnAction(event -> handleExit());
    }

    /**
     * Authenticates the user using the provided data.
     *
     * @param username and password authentication.
     */
    public void authenticate(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            System.out.println("Authentication successful.");
        } else {
            System.out.println("Authentication failed.");
            showAlert("Authentication failed. Please try again.");
        }
    }
}
