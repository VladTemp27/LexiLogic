package org.amalgam.lexilogicclient.client.mainmenu;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.amalgam.lexilogicclient.MainController;

public class MainMenuController {
    //private variables
    @FXML
    private AnchorPane mainmenuPane;
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
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#D9E0A2, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #D9E0A2;"));
    }

    /**
     * Shows an alert to a user if there is an error.
     *
     * @param message
     */
    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
        // insert exception for exiting application
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
        playButton.setOnAction(event -> handlePlay());
        profileButton.setOnAction(event -> handleProfile());
        leaderboardsButton.setOnAction(event -> handleLeaderboards());
        historyButton.setOnAction(event -> handleHistory());
        exitButton.setOnAction(event -> handleExit());
    }
    /**
     * Gets the objects used.
     * This method returns a string indicating the type of objects used by the controller.
     *
     * @return A string representing the objects used.
     */
    //TODO: @Override
    private void getObjectsUsed() {
        //TODO: Return Value
        //return "user";
    }

    /**
     * Fetches and updates data remotely.
     * This method is called to update the data displayed in the UI.
     *
     */
    //TODO: @Override
    private void fetchAndUpdate() {
        //TODO: Fetching of Data
    }
}
