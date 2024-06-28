package org.amalgam.lexilogicserver.views.playermanagement;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.amalgam.lexilogicserver.ServerController;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.Player;

import java.util.List;

public class PlayerManagementController {

    @FXML
    private AnchorPane playerManagementPane;

    @FXML
    private Button backButton;

    @FXML
    private Button addPlayerButton;

    @FXML
    private TableView<Player> playerTableView;

    @FXML
    private TableColumn<Player, String> usernameColumn;

    @FXML
    private TableColumn<Player, Void> editColumn;

    @FXML
    private TableColumn<Player, Void> deleteColumn;

    private List<Player> players;

    private ServerController serverController;

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    private void addHoverEffectImage(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-opacity: 0.8;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-opacity: 1.0;"));
    }

    private void addHoverEffect(Button button, String color) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(" + color + ", -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + color + ";"));
    }

    private void addHoverEffectAdd(Button button) {
    button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#9CA16F, -10%);"));
    button.setOnMouseExited(e -> button.setStyle("-fx-background-color:  #9CA16F;"));
    }

    @FXML
    public void handleBackButton() {
        if (serverController != null) {
            serverController.loadServerMainMenu();
        } else {
            System.out.println("Server controller is not set.");
        }
    }

    @FXML
    public void handleAddPlayer() {
        if (serverController != null) {
            serverController.loadAddPlayer();
        } else {
            System.out.println("Server controller is not set.");
        }
    }

    private void addButtonToTable() {
        Callback<TableColumn<Player, Void>, TableCell<Player, Void>> cellFactory = new Callback<TableColumn<Player, Void>, TableCell<Player, Void>>() {
            @Override
            public TableCell<Player, Void> call(final TableColumn<Player, Void> param) {
                final TableCell<Player, Void> cell = new TableCell<Player, Void>() {

                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");

                    {
                        editButton.setStyle("-fx-background-color: #485613; -fx-text-fill: #fff8d6;");
                        deleteButton.setStyle("-fx-background-color: #E42323; -fx-text-fill: #fff8d6;");

                        addHoverEffect(editButton, "#485613");
                        addHoverEffect(deleteButton, "#E42323");

                        editButton.setOnAction(event -> {
                            Player player = getTableView().getItems().get(getIndex());
                            // Handle edit action
                        });

                        deleteButton.setOnAction(event -> {
                            Player player = getTableView().getItems().get(getIndex());
                            // Handle delete action
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(editButton);
                            setGraphic(deleteButton);
                        }
                    }
                };
                return cell;
            }
        };

        editColumn.setCellFactory(cellFactory);
        deleteColumn.setCellFactory(cellFactory);
    }

@FXML
public void initialize() {
    // Ensure that usernameColumn is properly set up
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

    // Add button to the table if necessary
    addButtonToTable();

    // Ensure players list is not null before setting it to the TableView
    if (players == null) {
        players = FXCollections.observableArrayList();
    }
    playerTableView.getItems().setAll(players);

    // Add hover effects to buttons
    addHoverEffectImage(backButton);
    addHoverEffectAdd(addPlayerButton);

    // Set button actions
    addPlayerButton.setOnAction(event -> handleAddPlayer());
    backButton.setOnAction(event -> handleBackButton());
}
}
