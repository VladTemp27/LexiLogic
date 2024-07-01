package org.amalgam.lexilogicserver.views.accountdeletion;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.amalgam.lexilogicserver.ServerController;
import org.amalgam.lexilogicserver.model.DAL.PlayerDAL;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.Player;

import java.util.LinkedList;
import java.util.Optional;

@Deprecated
public class AccountDeletionController {
    //private variables
    @FXML
    private AnchorPane accountDeletionPane;
    @FXML
    private Button backButton;
    @FXML
    private TableView<UserData> accountTable;
    @FXML
    private TableColumn<UserData, String> username;
    private ServerController serverController;

    /**
     * Sets the Main Controller.
     *
     * @param serverController
     */
    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    /**
     * Adds hover effect to the given button.
     *
     * @param button The button to add hover effect to.
     */
    private void addHoverEffectImage(Button button) {
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

    public static class UserData {
        private final SimpleStringProperty username;

        public UserData(String username) {
            this.username = new SimpleStringProperty(username);
        }

        public String getUsername() {
            return username.get();
        }

        public SimpleStringProperty usernameProperty() {
            return username;
        }
    }

    /**
     * Handles the back button
     */
    @FXML
    public void handleBackButton(){
        if(serverController !=null){
            serverController.loadServerMainMenu();
        }else {
            System.out.println("Server controller is not set.");
        }
    }

    @FXML
    public void populateAccountsTable() {
        username.setCellValueFactory(data -> data.getValue().usernameProperty());
    }

    @FXML
    public void initialize(){
        addHoverEffectImage(backButton);
        backButton.setOnAction(event -> handleBackButton());
        populateAccountsTable();
        accountTable.setItems(FXCollections.observableArrayList(getAccountsDataList()));

        accountTable.setRowFactory(tv -> {
            TableRow<UserData> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    UserData rowData = row.getItem();
                    handleUserDoubleClick(rowData);
                }
            });
            return row;
        });
    }

    private ObservableList<UserData> getAccountsDataList() {
        ObservableList<UserData> data = FXCollections.observableArrayList();
        LinkedList<Player> players = PlayerDAL.retrieveForDeletion();
        for (Player player : players) {
            data.add(new UserData(player.getUsername()));
        }
        return data;
    }

    private void handleUserDoubleClick(UserData userData) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete User");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to delete " + userData.getUsername() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            markUserForDeletion(userData);
        }
    }

    private void markUserForDeletion(UserData userData) {
        PlayerDAL.deletePlayer(userData.getUsername());
        accountTable.setItems(FXCollections.observableArrayList(getAccountsDataList()));
        showAlert(userData.getUsername() + " has been deleted.");
    }
}
