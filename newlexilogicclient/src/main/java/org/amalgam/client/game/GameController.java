package org.amalgam.client.game;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.client.MainController;
import org.amalgam.client.login.LoginController;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameController{

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    // Game private variables
    public static int currentRound;
    public static int roomID;
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
    public static String[][] fetchLetters = new String[4][5];
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
        Task<Void> t1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                gameModel.submitReadyPlayer(LoginController.username, roomID);
                return null;
            }
            @Override
            protected void succeeded() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if(state.equals("invalid_word")){
                    System.out.println("INVALID WORD");
                    // TODO:Prompt user invalid word
                }
                if(state.equals("staging")){
                    System.out.println("staging");
                    Platform.runLater(() -> {
                         try {
                            int x = 0;
                            for (String[] fetchLetter : fetchLetters) {
                                for (String s : fetchLetter) {
                                    letterLabels[x].setText(s);
                                    x++;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.out.println("fetchAndDisplayLetters");
                        }
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
                                    timer.cancel();
                                    gameStart();
                                }
                            }
                        });
                    }
                }, 1000, 1000);
                });
        }

        if(state.equals("game_started")){
            System.out.println("game_started");
            Platform.runLater(() -> {
                     try {
                        int x = 0;
                        for (String[] fetchLetter : fetchLetters) {
                            for (String s : fetchLetter) {
                                letterLabels[x].setText(s);
                                x++;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println("fetchAndDisplayLetters");
                    }


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
                                    timer.cancel();
                                    gameStart();
                                }
                            }
                        });
                    }
                }, 1000, 1000);
                });
        }
            }
            @Override
            protected void failed() {
                System.out.println("FAILED T1");
            }

            @Override
            protected void cancelled() {
                System.out.println("CANCELLED T1");
            }
        };

        executorService.submit(t1);
    }

    /**
     * Start the game of the program.
     */

    private void gameStart() {
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
                                timer.cancel();
                                checkRoundWinner();
                            }
                        }
                    });
                }
            }, 1000, 1000);

            Platform.runLater(() -> {
                 lexiTextfield.setOnKeyPressed(event -> {
                     if (event.getCode() == KeyCode.ENTER) {
                        String input = lexiTextfield.getText();
                        System.out.println(input);
                        yourLexiLabel.setText(input); // Display the input in a label
                        lexiTextfield.clear(); // Clear the text field

                            if (!input.isEmpty()) {
                                System.out.println("SUBMITTING WORD...");
                                Task<Void> task = new Task<Void>() {
                                    @Override
                                    protected Void call() throws Exception {
                                        // Verify the word using GameModel
                                        gameModel.verifyWord(input, LoginController.username, roomID);
                                        System.out.println("VERIFY WORD");
                                        return null;
                                    }

                                    @Override
                                    protected void succeeded() {
                                        super.succeeded();
                                        System.out.println("SUBMITTED WORD");
                                    }
                                };

                            executorService.submit(task);
                    } else {
                        showAlert("Please enter a word.");
                    }
                     }
                 });
            });
                Task<Void> t2 = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {

                                return null;
                            }
                        };

                        executorService.submit(t2);
    }

    private void checkRoundWinner() {
        // This method should determine the winner of the round and update the player's score.
        // For this example, let's assume we have a method `determineRoundWinner` that returns the player's username.

        // temp data to be fixed
        String roundWinner = determineRoundWinner();
        playerRoundsWon.put(roundWinner, playerRoundsWon.getOrDefault(roundWinner, 0) + 2);
        updateScores();

        if (playerRoundsWon.get(roundWinner) >= 3) {
            endGame(roundWinner);
        } else {
            roundCountdown();
            roundCountdownPane.setVisible(true);
        }
    }

    private String determineRoundWinner() {
        // Dummy implementation for determining the round winner.
        // Replace with actual logic to determine the winner of the round.
        return "Lou"; // Example: returning player1 as the round winner
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
        roundsWonNumberLabel.setText(String.valueOf(0));
//        score1Label.setText(String.valueOf(playerRoundsWon.getOrDefault("player1", 0)));
//        score2Label.setText(String.valueOf(playerRoundsWon.getOrDefault("player2", 0)));
//        score3Label.setText(String.valueOf(playerRoundsWon.getOrDefault("player3", 0)));
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

            // Initialize letter labels array
            letterLabels = new Label[]{firstLetter, secondLetter, thirdLetter, fourthLetter, fifthLetter,
                    sixthLetter, seventhLetter, eightLetter, ninthLetter, tenthLetter,
                    eleventhLetter, twelfthLetter, thirteenthLetter, fourteenthLetter, fifteenthLetter,
                    sixteenthLetter, seventeenthLetter, eighteenthLetter, nineteenthLetter, twentiethLetter};
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
        }
    }
    private String state = "";
    public void updateData(String json){
        System.out.println("GAME "+json);
        JsonElement rootElement = JsonParser.parseString(json);
        JsonObject rootObject = rootElement.getAsJsonObject();

        //Checker for state
        //This should be the logic inside updateData
        state = rootObject.get("state").getAsString();
          if(state.equals("game_done")){
//            String winner = rootObject.get("winner").getAsString();
              victoryPanel.setVisible(true);
          }

        //End Checker for state
        roomID = rootObject.get("room_id").getAsInt();
//        System.out.println("ROOM "+roomID);

        //For current round
        currentRound = rootObject.get("current_round").getAsInt();
//        System.out.println("Current Round: "+currentRound);
        //End

        //For character matrix
        JsonElement cElement =rootObject.get("char_matrix");
        wordBoxMatrix(cElement);

         //End

         //For Game room
//         JsonObject gameRoomObject = rootObject.getAsJsonObject("game_room");
//         for(String key : gameRoomObject.keySet()){
//            JsonObject currentObject = gameRoomObject.getAsJsonObject(key);
//
//            String username = currentObject.get("username").getAsString();
//            int points = currentObject.get("points").getAsInt();
//            boolean ready = currentObject.get("ready").getAsBoolean();
//            JsonArray words = currentObject.getAsJsonArray("words");
//            JsonArray dupedWords = currentObject.getAsJsonArray("dupedWords");
//            System.out.println(username+","+points+","+ready);
//
//         }
         //End

        //Round History
//        JsonElement roundHistory = rootObject.getAsJsonObject("rounds");
//        if (roundHistory != null) {
//            JsonObject round = roundHistory.g
//            for (String roundKeys : roundHistory.keySet()) {
//                JsonObject round = roundHistory.getAsJsonObject(roundKeys);
//                String s = round.get("round_1").getAsString();
//                System.out.println(s);
////            String roundWinner = roundKeys + " winner : "+roundHistory.get(roundKeys).getAsString();
////            System.out.println(roundWinner);
//            }
//        } else {
//            System.out.println("no rounds yet.");
//        }
        //End

    }

    private LinkedHashMap<String, String> parseRounds(JsonObject gameRoomJsonObject){
        LinkedHashMap<String, String> rounds = new LinkedHashMap<>();
        JsonObject roundsObject = gameRoomJsonObject.getAsJsonObject("rounds");

        JsonArray array = roundsObject.getAsJsonArray();


        int index = 0; // Start index at 0 for array indexing
        for (JsonElement element : array) {
            String roundKey = "round_" + (index++); // Use index and increment within the loop
            String winner = element.getAsString();
            rounds.put(roundKey, winner);
        }

//        for(String key: roundsObject.keySet()){
//            String winner = roundsObject.get(key).getAsString();
//            rounds.put(key, winner);
//        }


        return rounds;
    }

    public LinkedHashMap<String, Integer> getPoints(JsonObject rootObject){
        LinkedHashMap<String, Integer> pointsList = new LinkedHashMap<>();

        JsonObject jsonObject = rootObject.getAsJsonObject("gameRoom");

        for(String key : jsonObject.keySet()){
            JsonObject userObject = jsonObject.getAsJsonObject(key);

            int points = userObject.get("points").getAsInt();

            pointsList.put(key, points );
            System.out.println(key+":"+points);
        }
        return pointsList;
    }

    private static void wordBoxMatrix(JsonElement cElement) {
         JsonArray rowArray = cElement.getAsJsonArray();
         int x = 0;
         int y = 0;
         for (JsonElement element : rowArray) { //This iterates through the rows
             JsonArray colArray = element.getAsJsonArray();
             for(JsonElement colElement : colArray){ //This iterates through the col inside the rows
                 fetchLetters[x][y]=colElement.getAsString();
                 y++;
             }
             y=0;
             x++;
         }
    }

}
