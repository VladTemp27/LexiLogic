package org.amalgam.client.leaderboards;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import org.amalgam.client.login.LoginController;
import org.amalgam.client.login.LoginModel;

import java.util.List;

public class LeaderboardsController {
    @FXML
    private AnchorPane leaderboardsPane;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label rankLabel;
    @FXML
    private TableView<LeaderboardsData> leaderboardsTable;
    @FXML
    private TableColumn<LeaderboardsData, String> rank;
    @FXML
    private TableColumn<LeaderboardsData, String> username;
    @FXML
    private TableColumn<LeaderboardsData, Integer> score;
    @FXML
    private Button backButton;

    private MainController mainController;
    public static LeaderboardsModel leaderboardsModel = new LeaderboardsModel(MainController.orbConnection,
            LoginController.playerCallback);
    // public LeaderboardsModel leaderboardsModel = new LeaderboardsModel(MainController.orbConnection, LoginModel.playerCallback)

     // TODO: This should be moved into an object for client side
    public static class LeaderboardsData {
        private String rank;
        private String username;
        private Integer score;
        private int highestRank;
        private int highestScore;


        public LeaderboardsData(String rank, String username, int score) {
            this.rank = rank;
            this.username = username;
            this.score = score;
            }

            public String getRank() {
                return rank;
            }

            public String getUsername() {
                return username;
            }

            public int getScore() {
                return score;
            }

            public int getHighestRank() {
                return highestRank;
            }

            public void setHighestRank ( int highestRank){
                this.highestRank = highestRank;
            }

            public int getHighestScore () {
                return highestScore;
            }

            public void setHighestScore ( int highestScore){
                this.highestScore = highestScore;
            }
        }

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
    private void addHoverEffect(javafx.scene.control.Button button) {
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
     * Shows an alert to a user if there is an error.
     *
     * @param message
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
     * Populate the Leaderboards Table
     */
    @FXML
    public void populateLeaderboardsTable() {
        rank.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRank()));
        username.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));
        score.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getScore()).asObject());
    }
    /**
     * Initializes the controller
     * This method sets up the UI components and initializes the data model
     */
    @FXML
    public void initialize() {
        addHoverEffect(backButton);
        backButton.setOnAction(event -> handleBack());
        populateLeaderboardsTable();
        leaderboardsTable.setItems(FXCollections.observableArrayList(
                getLeaderboardsDataList()
        ));
        leaderboardsTable.setStyle("-fx-font-family: 'Brygada 1918';");
    }
    private ObservableList<LeaderboardsData> getLeaderboardsDataList() {
        ObservableList<LeaderboardsData> data = FXCollections.observableArrayList();
        String leaderboards = leaderboardsModel.getLeaderBoards();
        List<LeaderboardsData> leaderboardsDataList = JsonObjectParser.parseLeaderboardsData(leaderboards);
        if (leaderboardsDataList != null) {
                for (LeaderboardsData lb : leaderboardsDataList) {
                    data.add(lb);
                    if (lb.getUsername().equals(LoginController.username)){
                        rankLabel.setText(lb.rank);
                        scoreLabel.setText(String.valueOf(lb.getScore()));
                    }
            }
        }
       return data;
    }
}
