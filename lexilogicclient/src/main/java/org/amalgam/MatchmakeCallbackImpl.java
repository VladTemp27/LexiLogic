package org.amalgam;


import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.UIControllers.MatchmakeCallbackPOA;

public class MatchmakeCallbackImpl extends MatchmakeCallbackPOA {
    private UpdateDispatcher dispatcher;
    private String username;
    @Override
    public String username() {
        return this.username;
    }

    @Override
    public void username(String newUsername) {
        this.username = newUsername;
    }

    public void setDispatcher(UpdateDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void joiningCall(String jsonString) throws InvalidRequestException {
        dispatcher.update(jsonString);
    }
}
