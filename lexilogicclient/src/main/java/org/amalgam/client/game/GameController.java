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
import org.amalgam.backend.microservices.objectparser.JsonObjectParser;
import org.amalgam.client.MainController;
import org.amalgam.client.UIPathResolver;
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
    public String[][] fetchLetters = new String[4][5];
    private int currentRound;
    private int roomID;
    public static int gameRoomID;

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
        final int[] finalGameTime = {30};
        Platform.runLater(() -> {
            populateWordMatrix();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        timeLabel.setText(String.format("00:%d", finalGameTime[0]));
                        if (finalGameTime[0] > 0) {
                            finalGameTime[0]--;
                            timeLabel.setText(String.format("00:%d", finalGameTime[0]));
                            if (finalGameTime[0] == 0) {
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

    private void updateScores(LinkedHashMap<String, Integer> username_points_per_round) {
        Map<String, Integer> topNPlayer = username_points_per_round.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        int x = 0;
        for (String username : topNPlayer.keySet()){
            int pts = topNPlayer.get(username);
            int finalX = x;
            Platform.runLater(() -> {
                arr_playerName_label[finalX].setText(username);
                arr_playerScore_label[finalX].setText(String.valueOf(pts));
            });
            x++;
        }
    }

    /**
     * Initializes the controller.
     * This method sets up the UI components and initializes the data model.
     */
    @FXML
    public void initialize() {
        try {
            initComponents();
            Platform.runLater(this::initGameComponents);
            LoginController.playerCallbackImpl.setControllerInterface(this); // initialize the interface of the callback of a player
            String response = LoadingController.response;
            gameRoomID = Integer.parseInt(Objects.requireNonNull(JsonObjectParser.parseMatchMaking(response, "gameRoomID")));
            gameModel = new GameModel(MainController.orbConnection);
            Thread.sleep(1000);
            gameModel.submitReadyHandshake(LoginController.username, gameRoomID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void initComponents() {
        victoryPanel.setVisible(false);
        gameOverPanel.setVisible(false);
        roundsWonNumberLabel.setText("0");

        // Initialize letter labels array
        arr_letter_label = new Label[]{firstLetter, secondLetter, thirdLetter, fourthLetter, fifthLetter,
                sixthLetter, seventhLetter, eightLetter, ninthLetter, tenthLetter,
                eleventhLetter, twelfthLetter, thirteenthLetter, fourteenthLetter, fifteenthLetter,
                sixteenthLetter, seventeenthLetter, eighteenthLetter, nineteenthLetter, twentiethLetter};

        arr_playerName_label = new Label[]{player1Label, player2Label, player3Label};

        arr_playerScore_label = new Label[]{player1ScoreLabel, player2ScoreLabel, player3ScoreLabel};

        backbtnVictory.setOnAction(event -> victoryPanelBackButton());
        backbtnDefeeat.setOnAction(event -> defeatPanelBackButton());
    }

    private void initGameComponents() {
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
        MainController.changeScreen(UIPathResolver.main_menu_path);
    }

    @FXML
    private void victoryPanelBackButton() {
        MainController.changeScreen(UIPathResolver.main_menu_path);
    }
    int x = 0; // reverse guard clause
    private void updateData(String json){
//        System.out.println("GAME "+json);
        JsonElement rootElement = JsonParser.parseString(json);
        JsonObject rootObject = rootElement.getAsJsonObject();

        //Checker for state
        String state = rootObject.get("state").getAsString();

        if (rootObject.get("room_id") != null) {
            roomID = rootObject.get("room_id").getAsInt();
        }

        JsonObject gameRoomObject = rootObject.getAsJsonObject("game_room");
        if (state.equals("staging")) { // components of game is initialized before game begins
            currentRound = rootObject.get("current_round").getAsInt();
            x=currentRound;
            wordBoxMatrix(rootObject);
            parseRounds(gameRoomObject);
            roundCountdown();
        }
        if (state.equals("game_started")) {
            int capacity = rootObject.get("capacity").getAsInt();
            fetchPoints(gameRoomObject, capacity);
            if (x==currentRound) gameStart();
            x++;
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
        } if (state.equals("self_duplicate")){
            showAlert("You've already entered that word!");
        } if (state.equals("duped")) {
            showAlert("You've been duped");
        } if (state.equals("duped_word")){
            showAlert("You have duped someone!");
        }
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

    public void fetchPoints(JsonObject gameRoomObject, int capacity){
        try {
            LinkedHashMap<String, Integer> pointsList = new LinkedHashMap<>();
            System.out.println("PARSING POINTS");
            for (int i=0; i<capacity; i++){
                String key = "player_" + i;
                JsonObject playerObject = gameRoomObject.getAsJsonObject(key);
                String player_name = playerObject.get("username").getAsString();
                int player_pts = playerObject.get("points").getAsInt();
                System.out.println(player_name + " " + player_pts);
                pointsList.put(player_name, player_pts);
            }
            updateScores(pointsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
