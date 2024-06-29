package org.amalgam.lexilogicserver.views.playermanagement;

import org.amalgam.lexilogicserver.model.DAL.PlayerDAL;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.Player;

import java.util.LinkedList;

public class PlayerManagementModel {
    public static LinkedList<Player> fetchPlayers(){
        return PlayerDAL.getAllPlayers();
    }
    public static void addPlayer (String username, String password ){
        PlayerDAL.insertNewPlayer(username,password);
    }

    public static void deletePlayer(String username){
        PlayerDAL.deletePlayer(username);
    }
}
