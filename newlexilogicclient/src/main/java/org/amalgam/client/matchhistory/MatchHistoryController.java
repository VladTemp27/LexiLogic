package org.amalgam.client.matchhistory;

import com.google.gson.*;
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
import org.amalgam.backend.microservices.objectparser.JsonObjectParser;
import org.amalgam.client.MainController;
import org.amalgam.client.leaderboards.LeaderboardsController;
import org.amalgam.client.leaderboards.LeaderboardsModel;
import org.amalgam.client.login.LoginController;

import java.util.List;

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
    private TableColumn<MatchData, String> standing;
    @FXML
    private TableColumn<MatchData, Integer> score;
    @FXML
    private Button backButton;
    private MainController mainController;
    public MatchHistoryModel matchHistoryModel = new MatchHistoryModel(MainController.orbConnection, LoginController.playerCallback);
    public LeaderboardsModel leaderboardsModel = new LeaderboardsModel(MainController.orbConnection,
            LoginController.playerCallback);

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

    //TODO: This should be moved into an Object for Client Side
    private static class MatchData {
        private String standing;
        private int score;
        private int highestRank;
        private int highestScore;

        public MatchData(String standing, int score) {
            this.standing = standing;
            this.score = score;
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
        standing.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStanding()));
        score.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getScore()).asObject());
    }

    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        addHoverEffect(backButton);
        backButton.setOnAction(event -> handleBack());
        List<LeaderboardsController.LeaderboardsData> leaderboardsDataList =
                JsonObjectParser.parseLeaderboardsData(leaderboardsModel.getLeaderBoards());
        for (LeaderboardsController.LeaderboardsData lb : leaderboardsDataList) {
            if (lb.getUsername().equals(LoginController.username)) {
                rankLabel.setText(lb.getRank());
                scoreLabel.setText(String.valueOf(lb.getScore()));
            }
        }
        populateMatchTable();
        matchTable.setItems(FXCollections.observableArrayList(
                getMatchHistoryDataList()
        ));
        matchTable.setStyle("-fx-font-family: 'Brygada 1918';");
    }

    private ObservableList<MatchData> getMatchHistoryDataList() {
        ObservableList<MatchData> matchHistoryData = FXCollections.observableArrayList();
        JsonElement rootElement = JsonParser.parseString(matchHistoryModel.getMatchHistory());
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonArray array = rootObject.getAsJsonArray("lobby");
        for(JsonElement element : array){
            JsonObject cObject = element.getAsJsonObject();
            JsonElement lobbyID = cObject.get("lobbyID");
            JsonElement username = cObject.get("username");
            JsonElement score = cObject.get("score");
            JsonElement createdBy = cObject.get("createdBy");
            JsonElement winner = cObject.get("winner");

            if (LoginController.username.equals(winner.getAsString())) {
                matchHistoryData.add(new MatchData("Win",Integer.parseInt(score.getAsString())));
            } else {
                matchHistoryData.add(new MatchData("Lose",Integer.parseInt(score.getAsString())));
            }
        }
        return matchHistoryData;
    }
}
