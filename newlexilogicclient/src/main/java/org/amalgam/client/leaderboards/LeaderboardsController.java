package org.amalgam.client.leaderboards;

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
import org.amalgam.client.login.LoginModel;

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
    public LeaderboardsModel leaderboardsModel = new LeaderboardsModel(MainController.orbConnection, LoginController.playerCallback);
    // public LeaderboardsModel leaderboardsModel = new LeaderboardsModel(MainController.orbConnection, LoginModel.playerCallback)

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

    // TODO: ???
    private ObservableList<LeaderboardsData> getLeaderboardsDataList() {
        ObservableList<LeaderboardsData> data = FXCollections.observableArrayList();
        String leaderboards = leaderboardsModel.getLeaderBoards();
        System.out.println(leaderboards);
        String[] leaderboardsArray = leaderboards.split(",");
        for (int i=1; i<leaderboardsArray.length + 1; i++) {
            if (i % 3 == 0){
                if (leaderboardsArray[i-3].contains(LoginController.username)){ // temporary solution
                    rankLabel.setText(leaderboardsArray[i-1]);
                    scoreLabel.setText(leaderboardsArray[i-2]);
                }
                data.add(new LeaderboardsData(leaderboardsArray[i-1], leaderboardsArray[i-3],
                        Integer.parseInt(leaderboardsArray[i-2])));
            }
        }
        return data;
    }
    // TODO: Should be moved into a separated data class
    // For Testing

//    public ObservableList<LeaderboardsData> leaderboardsDataList = FXCollections.observableArrayList(
//            new LeaderboardsData("1", "Georcelle Nuarin", 1200),
//            new LeaderboardsData("2", "Mark Lestat", 1100),
//            new LeaderboardsData("3", "Lenar Domingo", 1000)
//    );

    // TODO: This should be moved into an object for client side
    private static class LeaderboardsData {
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

//            leaderboardsTable.setItems(leaderboardsDataList);
        }

        /**
         * Populates the Highest Rank of the Player
         */
        @FXML
        public void populateRank() {
            //TODO: Get the Highest Rank of the Player
            rankLabel.setText("2"); // For Testing
        }

        /**
         * Populates the Highest Score of the Player
         */
        @FXML
        public void populateScore() {
            //TODO: Get the Highest Score of the Player
            scoreLabel.setText("999");
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

}
