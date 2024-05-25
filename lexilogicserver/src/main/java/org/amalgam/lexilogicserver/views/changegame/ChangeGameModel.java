package org.amalgam.lexilogicserver.views.changegame;

import org.amalgam.lexilogicserver.model.microservices.gamesettings.SettingsHandler;

public class ChangeGameModel {
    public static void changeQueueTime(int newTIme){
        SettingsHandler.setQueueTime(newTIme);
    }

}
