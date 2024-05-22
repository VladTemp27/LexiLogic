package org.amalgam.lexilogicserver.views.addplayer;

import org.amalgam.lexilogicserver.model.DAL.PlayerDAL;
import org.amalgam.lexilogicserver.model.serviceimpl.PlayerServiceImpl;

import java.sql.SQLException;

public class AddPlayerModel {
    public static void process (String username, String password ){
        PlayerDAL.insertNewPlayer(username,password);
    }
}
