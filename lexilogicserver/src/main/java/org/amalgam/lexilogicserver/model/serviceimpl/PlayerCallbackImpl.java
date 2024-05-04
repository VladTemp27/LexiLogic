package org.amalgam.lexilogicserver.model.serviceimpl;

import org.amalgam.Service.PlayerExceptions.InvalidRequest;
import org.amalgam.Service.PlayerServiceModule.PlayerCallbackPOA;
import org.amalgam.Utils.Objects.Player;

public class PlayerCallbackImpl extends PlayerCallbackPOA {
    @Override
    public void uiCall() throws InvalidRequest {

    }

    @Override
    public void setUser(Player player) throws InvalidRequest {

    }

    @Override
    public Player getPlayer() throws InvalidRequest {
        return null;
    }
}
