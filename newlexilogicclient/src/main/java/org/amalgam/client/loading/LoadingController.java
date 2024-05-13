package org.amalgam.client.loading;

import javafx.fxml.FXML;
import org.amalgam.client.MainController;

public class LoadingController {
    private MainController mainController;
    /**
     * Sets the Main Controller.
     *
     * @param mainController
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    /**
     * Gets the objects used.
     * This method returns a string indicating the type of objects used by the controller.
     *
     * @return A string representing the objects used.
     */
    //TODO: @Override
    private void getObjectsUsed() {
        //TODO: Return Value
        //return "user";
    }

    /**
     * Fetches and updates data remotely.
     * This method is called to update the data displayed in the UI.
     *
     */
    //TODO: @Override
    private void fetchAndUpdate() {
        //TODO: Fetching of Data
    }

    @FXML
    public void initialize() {
        // animation for the loading log (c.o lestat)
    }
}
