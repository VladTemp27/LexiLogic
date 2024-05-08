package org.amalgam.backend.microservices;

import org.amalgam.Service.PlayerServiceModule._PlayerServiceStub;
import org.amalgam.Utils.Exceptions.GameHistoryUnavailableException;
import org.amalgam.Utils.Exceptions.InGameException;
import org.amalgam.Utils.Objects.Lobby;

public class FetchMatchHistoryService {
    _PlayerServiceStub playerServiceStub = new _PlayerServiceStub();

    public Lobby[] process(String playerName) throws GameHistoryUnavailableException, InGameException {
        return playerServiceStub.getGameHistory(playerName);
    }
}
