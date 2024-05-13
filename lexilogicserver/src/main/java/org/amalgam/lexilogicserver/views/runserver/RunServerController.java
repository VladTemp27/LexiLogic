package org.amalgam.lexilogicserver.views.runserver;

import javafx.fxml.FXML;
import org.amalgam.lexilogicserver.ServerController;

public class RunServerController {
    private ServerController serverController;

    /**
     * Sets the Main Controller.
     *
     * @param mainController
     */
    public void setServerController(ServerController mainController) {
        this.serverController = mainController;
    }

     /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {

    }
}
