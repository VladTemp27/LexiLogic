package org.amalgam.lexilogicserver;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.amalgam.lexilogicserver.model.microservices.daemonHandler.ORBDException;
import org.amalgam.lexilogicserver.model.microservices.daemonHandler.ORBDOperationCallback;
import org.amalgam.lexilogicserver.model.microservices.serverHandler.ORBServerCallback;
import org.amalgam.lexilogicserver.views.accountdeletion.AccountDeletionController;
import org.amalgam.lexilogicserver.views.addplayer.AddPlayerController;
import org.amalgam.lexilogicserver.views.changegame.ChangeGameController;
import org.amalgam.lexilogicserver.views.runorbd.RunORBDRunningController;
import org.amalgam.lexilogicserver.views.runserver.RunServerRunningController;
import org.amalgam.lexilogicserver.views.servermainmenu.ServerMainMenuController;
import org.amalgam.lexilogicserver.views.runorbd.RunORBDController;
import org.amalgam.lexilogicserver.views.runserver.RunServerController;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Future;

public class ServerController implements ORBDOperationCallback,ORBServerCallback {
    public Stage stage;
    public static ServerMainMenuController serverMainMenuController;
    public static AnchorPane serverMainMenuPane;

    public static ChangeGameController changeGameController;
    public static AnchorPane changeGamePane;

    public static RunORBDController runORBDController;
    public static AnchorPane runORBDPane;

    public static RunServerController runServerController;
    public static AnchorPane runServerPane;

    public static AccountDeletionController accountDeletionController;
    public static AnchorPane accountDeletionPane;

    public static RunORBDRunningController runORBDRunningController;
    public static AnchorPane runORBDRunningPane;

    public static Future<Integer> ORBExitCode;
    public static boolean isServerRunning = false;
    /**
     * Getters and Setters of Controllers and Panels
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ServerMainMenuController getServerMainMenuController() {
        return serverMainMenuController;
    }

    public ChangeGameController getChangeGameController() {
        return changeGameController;
    }

    public RunORBDController getRunORBDController() {
        return runORBDController;
    }

    public RunServerController getRunServerController() {
        return runServerController;
    }
    public AccountDeletionController getAccountDeletionController(){return accountDeletionController;}

    public RunORBDRunningController getRunORBDRunningController(){return runORBDRunningController;}

    /**
     * Method to start the server
     */
    public void startServer() throws Exception {
        // Initialize and start the server
        Server server = new Server(); // Create an instance of the Server class
        server.start(stage);
        isServerRunning = true; // Set server running status to true
        System.out.println("Server started");
    }

    /**
     * Method to stop the server by closing the main stage
     */
    public void stopServer() {
        if (stage != null) {
            stage.close();
            isServerRunning = false; // Set server running status to false
            System.out.println("Server stopped");
        } else {
            System.out.println("Server is not running");
        }
    }
    /**
     * Loads and displays the server main menu view.
     */
    public void loadServerMainMenu() {
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/server/views/servermainmenu/servermainmenu-view.fxml"));
            AnchorPane serverMainMenuPane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");
            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(serverMainMenuPane);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            ServerMainMenuController serverMainMenuController = fxmlLoader.getController();
            serverMainMenuController.setServerController(this);
            serverMainMenuController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the run server view.
     */
    public void loadRunServer() {
//        ORBServer orbServer = new ORBServer(orbServerCallback, )
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/server/views/runserver/runserver-view.fxml"));
            AnchorPane runServer = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");
            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(runServer);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            RunServerController runServerController = fxmlLoader.getController();
            runServerController.setServerController(this);
            runServerController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the run server view.
     */
    public void loadRunServerRunning() {
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/server/views/runserver/runserverrunning-view.fxml"));
            AnchorPane runServerRunning = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");
            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(runServerRunning);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            RunServerRunningController runServerRunningController = fxmlLoader.getController();
            runServerRunningController.setServerController(this);
            runServerRunningController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the run orbd view.
     */
    public void loadRunORBD() {
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/server/views/runorbd/runorbd-view.fxml"));
            AnchorPane runORBD = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");
            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(runORBD);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            RunORBDController runORBDController = fxmlLoader.getController();
            runORBDController.setServerController(this);
            runORBDController.initialize();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.exit(0);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the change game view.
     */
    public void loadChangeGame() {
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/server/views/changegame/changegame-view.fxml"));
            AnchorPane changeGame = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");
            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(changeGame);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            ChangeGameController changeGameController = fxmlLoader.getController();
            changeGameController.setServerController(this);
            changeGameController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads and displays the add player view.
     */
    public void loadAddPlayer() {
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/server/views/addplayer/addplayer-view.fxml"));
            AnchorPane addPlayer = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");
            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(addPlayer);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            AddPlayerController addPlayerController = fxmlLoader.getController();
            addPlayerController.setServerController(this);
            addPlayerController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load and displays the Account Deletion view
     */
    public void loadAccountDeletion(){
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/server/views/accountdeletion/accountdeletion-view.fxml"));
            AnchorPane accountDeletionPane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");
            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(accountDeletionPane);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            AccountDeletionController accountDeletionController = fxmlLoader.getController();
            accountDeletionController.setServerController(this);
            accountDeletionController.initialize();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.exit(0);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRunORBDRunningView(){
        try {
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/BowlbyOneSC.ttf"), 20);
            Font.loadFont(getClass().getResourceAsStream("/org/amalgam/fonts/Brygada1918.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/amalgam/server/views/runorbd/runorbdrunning-view.fxml"));
            AnchorPane runORBDPane = fxmlLoader.load();

            InputStream inputStream = getClass().getResourceAsStream("/org/amalgam/icons/Logo.png");
            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: Logo.png");
            }

            Scene scene = new Scene(runORBDPane);

            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling the panel.");
            }

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lexi Logic");
            RunORBDRunningController runORBDRunningController = fxmlLoader.getController();
            runORBDRunningController.setServerController(this);
            runORBDRunningController.initialize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void notifyOrbExit() throws ORBDException {
        try {
            //TODO: Prompt with ui that ORB has exited
            System.out.println("ORB has exited with exit code: ");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setProcessObject(Process process) {
    }

    @Override
    public void notifyServerShutdown() {

    }

    @Override
    public void notifyServantsBinded() {

    }
}

