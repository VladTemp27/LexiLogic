package org.amalgam.lexilogicserver.views.playermanagement;

import org.amalgam.lexilogicserver.model.DAL.PlayerDAL;

public class PlayerManagementModel {
    public static void process (String username, String password ){
        PlayerDAL.insertNewPlayer(username,password);
    }
}
