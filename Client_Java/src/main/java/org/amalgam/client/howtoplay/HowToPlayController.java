package org.amalgam.client.howtoplay;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.amalgam.client.MainController;
import org.amalgam.client.UIPathResolver;

public class HowToPlayController {
    //private variables
    @FXML
    private AnchorPane howToPlayPane;
    @FXML
    private Button backButton;
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

    @FXML
    public void handleBack(){
        MainController.changeScreen(UIPathResolver.main_menu_path);
    }

    @FXML
    public void initialize(){
        addHoverEffectImage(backButton);
        backButton.setOnAction(event -> handleBack());
    }

}
