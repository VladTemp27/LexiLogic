package org.amalgam.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.amalgam.backend.microservices.serverconnection.ORBConnection;
import org.amalgam.client.game.GameController;
import org.amalgam.client.howtoplay.HowToPlayController;
import org.amalgam.client.leaderboards.LeaderboardsController;
import org.amalgam.client.loading.LoadingController;
import org.amalgam.client.login.LoginController;
import org.amalgam.client.mainmenu.MainMenuController;
import org.amalgam.client.matchhistory.MatchHistoryController;
import org.amalgam.client.profile.ProfileChangePassController;
import org.amalgam.client.profile.ProfileChangeUsernameController;
import org.amalgam.client.profile.ProfileController;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Design Technique: Aggregation [whole-part relationship] special form of association
 */

public class MainController {
    public static Stage primaryStage;
    public static Scene scene;
    public static FXMLLoader fxmlLoader;
    public InputStream inputStream;
    public static ORBConnection orbConnection;
    public static ArrayList<Class<?>> classes = new ArrayList<>();

    public MainController(Stage primaryStage) {
        MainController.primaryStage = primaryStage;

        classes.add(LoginController.class);
        classes.add(LoadingController.class);
        classes.add(GameController.class);
        classes.add(MainMenuController.class);
        classes.add(MatchHistoryController.class);
        classes.add(LeaderboardsController.class);
        classes.add(HowToPlayController.class);
        classes.add(ProfileController.class);
        classes.add(ProfileChangeUsernameController.class);
        classes.add(ProfileChangePassController.class);

        orbConnection = new ORBConnection(2121, "localhost");
        try {
            orbConnection.start();
        } catch (InvalidName | AdapterInactive e) {
            throw new RuntimeException(e);
        }

        try {
            indexView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

	}

    private void indexView() throws IOException {
        Font.loadFont(getClass().getResourceAsStream(UIPathResolver.font1_path), 20);
        Font.loadFont(getClass().getResourceAsStream(UIPathResolver.font2_path), 20);
        fxmlLoader = new FXMLLoader(getClass().getResource(UIPathResolver.login_path));
        inputStream = getClass().getResourceAsStream(UIPathResolver.axel_logo_path);
        Image image = new Image(Objects.requireNonNull(inputStream));
        primaryStage.getIcons().add(image);
        scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.show(); // show the window
        primaryStage.setResizable(false);
        primaryStage.setTitle("Lexi Logic");
    }

    public static void changeScreen(String view_path) {
        fxmlLoader = new FXMLLoader(MainController.class.getResource(view_path));

        try {
            scene = new Scene(fxmlLoader.load());
            for (Class<?> cs : classes) {
                String[] arr_str = fxmlLoader.getController().toString().split("@");
                String[] arr_str1 = arr_str[0].split("\\.");
                String[] cs_arr_str = cs.toString().split("\\.");
                String s1 = arr_str1[4];
                String s2 = cs_arr_str[4];
                if (s1.equals(s2)) {
                    primaryStage.setScene(scene);
                    primaryStage.show(); // show the window
                    primaryStage.setResizable(false);
                    primaryStage.setTitle("Lexi Logic");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}