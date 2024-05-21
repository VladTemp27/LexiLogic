package org.amalgam;

import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.UIControllers.PlayerCallbackPOA;
import org.amalgam.client.game.GameController;
import org.omg.CORBA.*;
import org.omg.CORBA.Object;

import java.io.Serializable;

public class PlayerCallbackImpl extends PlayerCallbackPOA{
	private String username;
	private GameControllerInterface gameControllerInterface;
	@Override
	public String username() {
		return username;
	}

	@Override
	public void username(String newUsername) {
		this.username = newUsername;
	}

	@Override
	public void uiCall(String jsonString) throws InvalidRequestException {

	}
}
