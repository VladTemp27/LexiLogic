package org.amalgam.client.matchhistory;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.client.MainController;
import org.amalgam.client.login.LoginController;

public class MatchHistoryController{
    @FXML
    private AnchorPane matchHistoryPane;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label rankLabel;
    @FXML
    private TableView<MatchData> matchTable;
    @FXML
    private TableColumn<MatchData, String> gameID;
    @FXML
    private TableColumn<MatchData, String> standing;
    @FXML
    private TableColumn<MatchData, Integer> score;
    @FXML
    private Button backButton;

    private MainController mainController;
    public MatchHistoryModel matchHistoryModel = new MatchHistoryModel(MainController.orbConnection, LoginController.playerCallback);

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
        ImageView imageView = (ImageView) button.getGraphic();
        ColorAdjust colorAdjust = new ColorAdjust();

        button.setOnMouseEntered(e -> {
            colorAdjust.setBrightness(-0.3); // Decrease brightness to make it darker
            imageView.setEffect(colorAdjust);
        });

        button.setOnMouseExited(e -> {
            colorAdjust.setBrightness(0); // Reset brightness
            imageView.setEffect(colorAdjust);
        });
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

    // TODO: Should be moved into a separated data class
    // For Testing
//    private ObservableList<MatchData> matchDataList = FXCollections.observableArrayList(
//            new MatchData("1", "Win", 100),
//            new MatchData("2", "Loss", 50),
//            new MatchData("3", "Win", 120)
//    );

    //TODO: This should be moved into an Object for Client Side
    private static class MatchData {
        private String gameID;
        private String standing;
        private int score;
        private int highestRank;
        private int highestScore;

        public MatchData(String gameID, String standing, int score) {
            this.gameID = gameID;
            this.standing = standing;
            this.score = score;
        }

        public String getGameID() {
            return gameID;
        }

        public String getStanding() {
            return standing;
        }

        public int getScore() {
            return score;
        }

        public int getHighestRank() {
            return highestRank;
        }

        public void setHighestRank(int highestRank) {
            this.highestRank = highestRank;
        }

        public int getHighestScore() {
            return highestScore;
        }

        public void setHighestScore(int highestScore) {
            this.highestScore = highestScore;
        }
    }

    /**
     * Shows the main menu panel when pressed.
     */
    @FXML
    public void handleBack() {
        if (mainController != null) {
            mainController.loadMainMenuView();
        } else {
            System.out.println("MainController is not set.");
        }
    }

    /**
     * Populates the Match History Table.
     */
    @FXML
    public void populateMatchTable() {
        gameID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGameID()));
        standing.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStanding()));
        score.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getScore()).asObject());

//        matchTable.setItems(matchDataList);
    }

    /**
     * Populates the Highest Rank of the Player
     */
    @FXML
    public void populateRank() {
        //TODO: Get the Highest Rank of the Player
        rankLabel.setText("1"); // For Testing
    }

    /**
     * Populates the Highest Score of the Player
     */
    @FXML
    public void populateScore() {
        //TODO: Get the Highest Score of the Player
        scoreLabel.setText("5000");
    }

    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        addHoverEffect(backButton);
        backButton.setOnAction(event -> handleBack());
        populateMatchTable();
        matchTable.setItems(FXCollections.observableArrayList(
                getMatchHistoryDataList()
        ));
        matchTable.setStyle("-fx-font-family: 'Brygada 1918';");
    }

    private ObservableList<MatchData> getMatchHistoryDataList() {
        ObservableList<MatchData> matchHistoryData = FXCollections.observableArrayList();
        matchHistoryModel.getMatchHistory();
        return matchHistoryData;
    }
}
