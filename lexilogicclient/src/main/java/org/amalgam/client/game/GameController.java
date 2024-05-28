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
import org.amalgam.UpdateDispatcher;
import org.amalgam.client.MainController;
import org.amalgam.client.loading.LoadingController;
import org.amalgam.client.login.LoginController;

import java.util.*;
import java.util.stream.Collectors;

public class GameController implements UpdateDispatcher {
    @FXML
    private AnchorPane gamePane;
    @FXML
    private  AnchorPane gameOverPanel;
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
    private Label player1ScoreLabel;
    @FXML
    private Label player2ScoreLabel;
    @FXML
    private Label player3ScoreLabel;
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
    private Timer timer;
    @FXML
    private Label roundStartingInLabel;
    @FXML
    private Label RCTimeLabel;
    @FXML
    private Label RCroundNumberLabel;
    @FXML
    private Button playAgainButtonGO;
    @FXML
    private Button backbtnDefeeat;
    @FXML
    private Button playAgainButtonV;
    @FXML
    private Button backbtnVictory;
    private Label[] arr_letter_label; // Array to hold letter labels
    private Label[] arr_playerName_label;
    private Label[] arr_playerScore_label;
    private static GameModel gameModel;
    private int totalRoundWon = 0;
    private int totalPts=0;
    public String[][] fetchLetters = new String[4][5];
    private int currentRound;
    private int roomID;

    private static void showAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    /**
     * round counter before game to start
     */
    private void roundCountdown() {
        final int[] countdown = {5};
        Platform.runLater(() -> {
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

                                Task<Void> t1 = new Task<Void>() {
                                    @Override
                                    protected Void call() throws Exception {
                                        gameModel.submitReadyPlayer(LoginController.username, roomID);
                                        return null;
                                    }

                                    @Override
                                    protected void succeeded() {
                                        super.succeeded();
                                        try {
                                            Thread.sleep(500);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                        System.out.println("SUCCESS");
                                    }
                                };
                                LoadingController.executorService.submit(t1);
                            }

                        }
                    });
                }
            }, 1000, 1000);
        });

    }

    /**
     * Start the game of the program.
     */

    private void gameStart() {
        Platform.runLater(this::initComponents);

        final int[] gameTime = {30};
        Platform.runLater(() -> {
            populateWordMatrix();
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
                                roundCountdownPane.setVisible(true);
                                timer.cancel();
                            }
                        }
                    });
                }
            }, 1000, 1000);
        });

    }

    private void populateWordMatrix() {
        try {
            int x = 0;
            for (String[] fetchLetter : fetchLetters) {
                for (String s : fetchLetter) {
                    arr_letter_label[x].setText(s);
                    x++;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("fetchAndDisplayLetters");
        }
    }

    // TODO: fix update of score; display "total score for the entire game"(sigma level) | "total score per round"(easy)
    private void updateScores(LinkedHashMap<String, Integer> username_points) {
        for (String username_1 : username_points.keySet()) {
            int pts_1 = username_points.get(username_1);
            for (String username_2 : username_points.keySet()) {
                int pts_2 = username_points.get(username_2);

                if (pts_1 > pts_2) {

                }
            }

        }
    }

    /**
     * Ends the game and shows the winner.
     */
    private void endGame(String winner) {
        showAlert("Game Over! Winner: " + winner);
    }

    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        try {
            gameModel = new GameModel(MainController.orbConnection);
            victoryPanel.setVisible(false);
            gameOverPanel.setVisible(false);
            roundsWonNumberLabel.setText("0");
            LoginController.playerCallbackImpl.setControllerInterface(this); // initialize the interface of the callback of a player

            // Initialize letter labels array
            arr_letter_label = new Label[]{firstLetter, secondLetter, thirdLetter, fourthLetter, fifthLetter,
                    sixthLetter, seventhLetter, eightLetter, ninthLetter, tenthLetter,
                    eleventhLetter, twelfthLetter, thirteenthLetter, fourteenthLetter, fifteenthLetter,
                    sixteenthLetter, seventeenthLetter, eighteenthLetter, nineteenthLetter, twentiethLetter};

            backbtnVictory.setOnAction(event -> victoryPanelBackButton());
            backbtnDefeeat.setOnAction(event -> defeatPanelBackButton());

//            initComponents();
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

    private void initComponents() {
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

                    LoadingController.executorService.submit(task);
                } else {
                    showAlert("Please enter a word.");
                }
            }
        });
    }


    @FXML
    private void defeatPanelBackButton() {
    }

    @FXML
    private void victoryPanelBackButton() {
    }

    private void updateData(String json){
//        System.out.println("GAME "+json);
        JsonElement rootElement = JsonParser.parseString(json);
        JsonObject rootObject = rootElement.getAsJsonObject();

        //Checker for state
        String state = rootObject.get("state").getAsString();
        JsonObject gameRoomObject = rootObject.getAsJsonObject("game_room");
        if (state.equals("staging")) { // components of game is initialized before game begins
            //For current round
            System.out.println("STAGING");

            currentRound = rootObject.get("current_round").getAsInt();
            //End

            if (rootObject.get("room_id") != null) {
                roomID = rootObject.get("room_id").getAsInt();
            }

            //For character matrix
            wordBoxMatrix(rootObject);
            //End

            parseRounds(gameRoomObject);

            roundCountdown();
        }

        if (state.equals("game_started")) {
            gameStart();
        }

        if(state.equals("game_done")){
          Platform.runLater(() -> {
              String winner = rootObject.get("winner").getAsString();
              System.out.println(winner);
              if (Objects.equals(LoginController.username, winner)){
                    victoryPanel.setVisible(true);
              } else {
                    gameOverPanel.setVisible(true);
              }
          });
        }

        if (state.equals("invalid_word")) {
              showAlert("INVALID WORD");
        }

         //For Game room
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
    }

    public void parseRounds(JsonObject gameRoomObject){
        int previousRound = currentRound - 1;
        if (previousRound == 0) return;
        try {
            JsonObject roundsObject = gameRoomObject.getAsJsonObject("rounds");
            System.out.println("PARSING ROUNDS");
            String roundKey = "round_" + previousRound;
            String winner = roundsObject.get(roundKey).getAsString();
            System.out.println(roundKey + " " + winner);

            System.out.println("ROUND WINNER");
            if (winner.equals(LoginController.username)) { // winner for a round
                int roundWon = 1;
                totalRoundWon += roundWon;
                System.out.println(winner + " " + (totalRoundWon));
                Platform.runLater(() -> {
                    roundsWonNumberLabel.setText(String.valueOf(totalRoundWon));
                });
            } else {
                System.out.println(LoginController.username + " " + winner);
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    // TODO: fix parsing points of json; points object is null
    public LinkedHashMap<String, Integer> fetchPoints(JsonObject rootObject){
        try {
            LinkedHashMap<String, Integer> pointsList = new LinkedHashMap<>();
            JsonObject pointsObject = rootObject.getAsJsonObject("gameRoom");
            if (pointsObject != null) {
                for (String key : pointsObject.keySet()) {
                    JsonObject userObject = pointsObject.getAsJsonObject(key);

                    int points = userObject.get("points").getAsInt();

                    pointsList.put(key, points);
                    System.out.println(key + ":" + points);
                }
                updateScores(pointsList);
            } else {
                System.out.println("pointsObject is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void wordBoxMatrix(JsonObject rootObject) {
        JsonElement wordBoxElement = rootObject.get("char_matrix");
        JsonArray rowArray = wordBoxElement.getAsJsonArray();
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

    @Override
    public void update(String jsonString) {
        updateData(jsonString);
    }
}
