package org.amalgam.client.game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.ControllerInterface;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.amalgam.client.MainController;
import org.amalgam.client.login.LoginController;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.HashMap;
import java.util.Map;

public class GameController{
    // Game private variables
    @FXML
    private AnchorPane gamePane;
    @FXML
    private AnchorPane gameOverPanel;
    @FXML
    private AnchorPane victoryPanel;
    // Round Countdown private variables
    @FXML
    private AnchorPane roundCountdownPane;
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
    private Label nineteenthLetter;
    @FXML
    private Label twentiethLetter;
    @FXML
    private TextField lexiTextfield;
    @FXML
    private Label yourLexiLabel;
    private Label[] letterLabels; // Array to hold letter labels
    private Timer timer;
    private int currentRound = 1;

    // Player round wins tracking
    private Map<String, Integer> playerRoundsWon = new HashMap<>();
    @FXML
    private Label roundStartingInLabel;
     @FXML
    private Label RCTimeLabel;
    @FXML
    private Label RCroundNumberLabel;
    @FXML
    private Button playAgainButtonGO;
    @FXML
    private Button backButtonGO;
    @FXML
    private Button playAgainButtonV;
    @FXML
    private Button backButtonV;
    // common private variables
    private GameModel gameModel;
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
        // Implementation needed
    }

    /**
     * Fetches and updates data remotely.
     * This method is called to update the data displayed in the UI.
     *
     */
    public void fetchAndUpdate(String jsonString, String dataType) throws InvalidRequestException {
        // Implementation needed
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * round counter before game to start
     */
    private void roundCountdown() {
        Platform.runLater(() -> {
            final int[] countdown = {5};
            RCTimeLabel.setText(String.format("00:0%d", countdown[0]));
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        if (countdown[0] > 0) {
                            countdown[0]--;
                            RCTimeLabel.setText(String.format("00:0%d", countdown[0]));
                            if (countdown[0] == 1) {
                                RCroundNumberLabel.setText("ROUND " + currentRound);
                            }
                            if (countdown[0] == 0) {
                                roundCountdownPane.setVisible(false);
                                gamePane.setVisible(true);
                                roundLabel.setText("ROUND " + currentRound);
                                startTimer();
                                timer.cancel();
                            }

                        }
                    });
                }
            }, 1000, 1000);
        });
    }

    private void startTimer() {
        Platform.runLater(() -> {
            final int[] gameTime = {30};
            timeLabel.setText(String.format("00:%d", gameTime[0]));
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        if (gameTime[0] > 0) {
                            gameTime[0]--;
                            timeLabel.setText(String.format("00:%d", gameTime[0]));
                            if (gameTime[0] == 0) {
                                System.out.println("ROUND ENDED");
                                timer.cancel();
                                checkRoundWinner();
                            }
                        }
                    });
                }
            }, 1000, 1000);
        });
    }

    /**
     * Fetch and display the letters in the word box.
     */
    private void fetchAndDisplayLetters(int roomID) {
        try {
//            char[][] letters = gameModel.fetchWordBox(roomID);
            char[][] letters = {
                    {'A','B','C','D'},
                    {'E','F','G','H'},
                    {'I','J','K','L'}
            };
            List<Character> consonants = new ArrayList<>();
            List<Character> vowels = new ArrayList<>();

            for (char[] row : letters) {
                for (char letter : row) {
                    if (isVowel(letter)) {
                        vowels.add(letter);
                    } else {
                        consonants.add(letter);
                    }
                }
            }
            Collections.shuffle(consonants);
            Collections.shuffle(vowels);

            List<Character> selectedLetters = new ArrayList<>();
            selectedLetters.addAll(vowels.subList(0, Math.min(vowels.size(), 7)));
            selectedLetters.addAll(consonants.subList(0, Math.min(consonants.size(), 13)));

            Collections.shuffle(selectedLetters);

            for (int i = 0; i < letterLabels.length && i < selectedLetters.size(); i++) {
                letterLabels[i].setText(String.valueOf(selectedLetters.get(i)));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("fetchAndDisplayLetters");
        }
    }

    private boolean isVowel(char letter) {
        return "AEIOU".indexOf(Character.toUpperCase(letter)) != -1;
    }

    private void checkRoundWinner() {
        // This method should determine the winner of the round and update the player's score.
        // For this example, let's assume we have a method `determineRoundWinner` that returns the player's username.
        String roundWinner = determineRoundWinner();
        playerRoundsWon.put(roundWinner, playerRoundsWon.getOrDefault(roundWinner, 0) + 1);
        updateScores();

        if (playerRoundsWon.get(roundWinner) >= 3) {
            endGame(roundWinner);
        } else {
            startNewRound();
        }
    }

    private String determineRoundWinner() {
        // Dummy implementation for determining the round winner.
        // Replace with actual logic to determine the winner of the round.
        return "player1"; // Example: returning player1 as the round winner
    }

    private void startNewRound() {
        currentRound++;
        fetchAndDisplayLetters(1); // Example roomID = 1
        startTimer();
        updateRoundLabel();
    }

    /**
     * Ends the game and shows the winner.
     */
    private void endGame(String winner) {
        showAlert("Game Over! Winner: " + winner);
    }

    /**
     * Updates the round label to display the current round number.
     */
    private void updateRoundLabel() {
        roundLabel.setText("Round: " + currentRound);
    }

    private void updateScores() {
        // Update the scores in the UI
        roundsWonNumberLabel.setText(String.valueOf(playerRoundsWon.getOrDefault("player1", 0)));
        score1Label.setText(String.valueOf(playerRoundsWon.getOrDefault("player1", 0)));
        score2Label.setText(String.valueOf(playerRoundsWon.getOrDefault("player2", 0)));
        score3Label.setText(String.valueOf(playerRoundsWon.getOrDefault("player3", 0)));
    }

    /**
     * Processes the text input from the lexicon text field.
     */
    private void processLexiconInput() {
        String input = lexiTextfield.getText().trim();
        if (!input.isEmpty()) {
            // Verify the word using GameModel
            gameModel.verifyWord(input);
            yourLexiLabel.setText(input); // Example: Display the input in a label
            lexiTextfield.clear(); // Clear the text field
        } else {
            showAlert("Please enter a word.");
        }
    }

    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        try {
            gameModel = new GameModel(MainController.orbConnection);
            gameOverPanel.setVisible(false);
            victoryPanel.setVisible(false);
            roundCountdown();
//
//            // Initialize letter labels array
//            letterLabels = new Label[]{firstLetter, secondLetter, thirdLetter, fourthLetter, fifthLetter,
//                    sixthLetter, seventhLetter, eightLetter, ninthLetter, tenthLetter,
//                    eleventhLetter, twelfthLetter, thirteenthLetter, fourteenthLetter, fifteenthLetter,
//                    sixteenthLetter, seventeenthLetter, eighteenthLetter, nineteenthLetter, twentiethLetter};
//
//            // Initialize player round wins
//            playerRoundsWon.put("player1", 0);
//            playerRoundsWon.put("player2", 0);
//            playerRoundsWon.put("player3", 0);
//
//            // Fetch and display letters
//            fetchAndDisplayLetters(1); // Example roomID = 1
//
//            // Add event handler for the lexicon text field to handle 'Enter' key press
//            lexiTextfield.setOnAction(event -> processLexiconInput());
//
//            // Update round label
//            updateRoundLabel();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("initializer error");
        }
    }

}
