package org.amalgam.lexilogicserver.views.changegame;

import com.sun.scenario.Settings;
import org.amalgam.lexilogicserver.model.microservices.gamesettings.SettingsHandler;

public class ChangeGameModel {
    public static void changeQueueTime(int newTIme){
        SettingsHandler.setQueueTime(newTIme);
    }
    public static int setQueueTime(){
        return SettingsHandler.getQueueTime();
    }
    public static void changeGameTime(int newTime){
        SettingsHandler.setGameTime(newTime);
    }
    public static int getGameTime(){
        return SettingsHandler.getGameTime();
    }

}
