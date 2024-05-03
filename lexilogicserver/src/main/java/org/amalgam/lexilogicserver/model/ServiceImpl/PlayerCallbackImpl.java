package org.amalgam.lexilogicserver.model.ServiceImpl;

import org.amalgam.Service.Game.GameServicePackage.Player;
import org.amalgam.Service.Game.PlayerCallbackPOA;
import org.amalgam.Service.GameExceptions.InvalidRequestException;

public class PlayerCallbackImpl extends PlayerCallbackPOA {
    @Override
    public void uiCall() throws InvalidRequestException {

    }

    @Override
    public void setUser(Player player) throws InvalidRequestException {

    }

    @Override
    public Player getPlayer() throws InvalidRequestException {
        return null;
    }
}
