package org.amalgam.lexilogicclient.client.matchhistory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;

public class MatchHistoryController {
    @FXML
    private AnchorPane matchHistoryPane;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label rankLabel;
    @FXML
    private TableColumn gameID;
    @FXML
    private TableColumn standing;
    @FXML
    private TableColumn score;
}
