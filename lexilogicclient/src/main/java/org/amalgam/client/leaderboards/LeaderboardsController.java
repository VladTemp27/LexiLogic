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
import org.amalgam.backend.microservices.objectparser.JsonObjectParser;
import org.amalgam.backend.referenceobjects.Leaderboard;
import org.amalgam.client.MainController;
import org.amalgam.client.UIPathResolver;
import org.amalgam.client.login.LoginController;

import java.util.List;

public class LeaderboardsController {
    @FXML
    private AnchorPane leaderboardsPane;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label rankLabel;
    @FXML
    private TableView<Leaderboard> leaderboardsTable;
    @FXML
    private TableColumn<Leaderboard, String> rank;
    @FXML
    private TableColumn<Leaderboard, String> username;
    @FXML
    private TableColumn<Leaderboard, Integer> score;
    @FXML
    private Button backButton;

    public static LeaderboardsModel leaderboardsModel = new LeaderboardsModel(MainController.orbConnection,
            LoginController.playerCallback);

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
        MainController.changeScreen(UIPathResolver.main_menu_path);
    }

    /**
     * Populate the Leaderboards Table
     */
    @FXML
    public void populateLeaderboardsTable() {
        leaderboardsTable.setItems(FXCollections.observableArrayList(
                fetchLeaderboards()
        ));
    }
    /**
     * Initializes the controller
     * This method sets up the UI components and initializes the data model
     */
    @FXML
    public void initialize() {
        addHoverEffect(backButton);
        backButton.setOnAction(event -> handleBack());
        initComponents();
        populateLeaderboardsTable();
        leaderboardsTable.setStyle("-fx-font-family: 'Brygada 1918';");
    }

    private void initComponents() {
        rank.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPlayerRank())));
        username.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));
        score.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPoints()).asObject());
    }

    private ObservableList<Leaderboard> fetchLeaderboards() {
        ObservableList<Leaderboard> data = FXCollections.observableArrayList();
        String leaderboards = leaderboardsModel.getLeaderBoards();
        List<Leaderboard> leaderboardList = JsonObjectParser.parseLeaderBoards(leaderboards);
        for (Leaderboard lb : leaderboardList) {
            data.add(lb);
            if (lb.getUsername().equals(LoginController.username)) {  // rank board
                rankLabel.setText(String.valueOf(lb.getPlayerRank()));
                scoreLabel.setText(String.valueOf(lb.getPoints()));
            }
        }
        return data;
    }
}
