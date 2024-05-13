package org.amalgam.lexilogicserver.views.servermainmenu;

import javafx.fxml.FXML;
import org.amalgam.lexilogicserver.ServerController;

public class ServerMainMenuController {
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
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {

    }
}
