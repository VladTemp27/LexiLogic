package org.amalgam.lexilogicserver.views.editplayer;

import org.amalgam.lexilogicserver.model.DAL.PlayerDAL;

public class EditPlayerModel {
    public static void process (String username, String password ){
        PlayerDAL.insertNewPlayer(username,password);
    }
}
