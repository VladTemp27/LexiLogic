package org.amalgam.client.game;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.amalgam.client.MainController;
import org.amalgam.client.mainmenu.MainMenuModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    // private variables
    @FXML
    private AnchorPane gamePane;
    @FXML
    private Label timeLabel; // for the 30 seconds
    @FXML
    private Label timeLeftLabel;
    @FXML
    private Label roundsWonLabel;
    @FXML
    private Label roundsWonNumberLabel; // if how many rounds won label
    @FXML
    private Label roundLabel;
    @FXML
    private Label player1Label;
    @FXML
    private Label player2Label;
    @FXML
    private Label player3Label;
    @FXML
    private Label score1Label;
    @FXML
    private Label score2Label;
    @FXML
    private Label score3Label;
    @FXML
    private GridPane wordBoxGridPane;
    @FXML
    private Label firstLetter;
    @FXML
    private Label secondLetter;
    @FXML
    private Label thirdLetter;
    @FXML
    private Label fourthLetter;
    @FXML
    private Label fifthLetter;
    @FXML
    private Label sixthLetter;
    @FXML
    private Label seventhLetter;
    @FXML
    private Label eightLetter;
    @FXML
    private Label ninthLetter;
    @FXML
    private Label tenthLetter;
    @FXML
    private Label eleventhLetter;
    @FXML
    private Label twelfthLetter;
    @FXML
    private Label thirteenthLetter;
    @FXML
    private Label fourteenthLetter;
    @FXML
    private Label fifteenthLetter;
    @FXML
    private Label sixteenthLetter;
    @FXML
    private Label seventeenthLetter;
    @FXML
    private Label eighteenthLetter;
    @FXML
    private Label ninteenthLetter;
    @FXML
    private Label twentiethLetter;
    @FXML
    private Label yourLexiLabel;
    private Label[] letterLabels; // Array to hold letter labels
    private Timer timer;

    @FXML
    private TextField lexiTextfield;
    private GameModel gameModel;
    private ORBConnection orbConnection;
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
    public void setObjectsUser(String objects) throws InvalidRequestException {

    }

    /**
     * Fetches and updates data remotely.
     * This method is called to update the data displayed in the UI.
     *
     */
    public void fetchAndUpdate(String jsonString, String dataType) throws InvalidRequestException {

    }

    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void startTimer() {
        final int[] timeRemaining = {30}; // Make timeRemaining final
        timeLabel.setText(String.valueOf(timeRemaining[0]));

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeRemaining[0]--; // Can access final variable
                timeLabel.setText(String.valueOf(timeRemaining[0]));

                if (timeRemaining[0] == 0) {
                    // Handle timer ending: start new round
                    timer.cancel();
                    startNewRound();
                }
            }
        }, 0, 1000); // Start immediately, then update every second
    }

    /**
     * Method for randomizing letters inside the word box
     */
    private void randomizeLetters() {
        String consonants = "BCDFGHJKLMNPQRSTVWXYZ";
        String vowels = "AEIOU";

        ArrayList<Character> letters = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            letters.add(consonants.charAt(i));
        }
        for (int i = 0; i < 7; i++) {
            letters.add(vowels.charAt(i));
        }

        Collections.shuffle(letters);

        for (int i = 0; i < letterLabels.length; i++) {
            letterLabels[i].setText(String.valueOf(letters.get(i)));
        }
    }
    private void startNewRound() {
        // Reset timer and randomize letters for new round
        randomizeLetters();
        startTimer();
     }

    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize(){
        // Initialize the game model
        gameModel = new GameModel(orbConnection);

        // Initialize letter labels array
        letterLabels = new Label[]{firstLetter, secondLetter, thirdLetter, fourthLetter, fifthLetter,
                sixthLetter, seventhLetter, eightLetter, ninthLetter, tenthLetter,
                eleventhLetter, twelfthLetter, thirteenthLetter, fourteenthLetter, fifteenthLetter,
                sixteenthLetter, seventeenthLetter, eighteenthLetter, ninteenthLetter, twentiethLetter};

        // Randomize letters
        randomizeLetters();

        // Start timer
        startTimer();
    }

}

