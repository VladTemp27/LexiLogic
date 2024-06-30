package org.amalgam.lexilogicserver.views.playermanagement;

import org.amalgam.lexilogicserver.model.DAL.PlayerDAL;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.Player;

import java.util.LinkedList;

public class PlayerManagementModel {

    private static Player selectedPlayer = null;

    public static void setSelectedPlayer(Player selectedPlayer) {
        PlayerManagementModel.selectedPlayer = selectedPlayer;
    }

    public static Player getSelectedPlayer() {
        return selectedPlayer;
    }

    public static LinkedList<Player> fetchPlayers(){
        return PlayerDAL.getAllPlayers();
    }
    public static void addPlayer (String username, String password ){
        PlayerDAL.insertNewPlayer(username,password);
    }
    public static void changeUsername(int id, String username){
        PlayerDAL.updateUsername(id, username);
    }

    public static void changePassword (int id, String username){
        PlayerDAL.updatePassword(id, username);
    }
    public static void deletePlayer(String username){
        PlayerDAL.deletePlayer(username);
    }
}
