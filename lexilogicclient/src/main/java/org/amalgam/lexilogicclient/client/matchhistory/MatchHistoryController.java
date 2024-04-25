package org.amalgam.lexilogicclient.client.matchhistory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.amalgam.lexilogicclient.MainController;

public class MatchHistoryController {
    @FXML
    private AnchorPane matchHistoryPane;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label rankLabel;
    @FXML
    private TableView matchTable;
    @FXML
    private TableColumn gameID;
    @FXML
    private TableColumn standing;
    @FXML
    private TableColumn score;

    private MainController mainController;

    /**
     * Sets the Main Controller.
     *
     * @param mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    @FXML
    public void initialize() {

    }
}
