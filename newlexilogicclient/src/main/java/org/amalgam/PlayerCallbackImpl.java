package org.amalgam;

import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.UIControllers.PlayerCallbackPOA;

public class PlayerCallbackImpl extends PlayerCallbackPOA{
	private String username;
	private GameControllerInterface gameControllerInterface;
	private ControllerInterface controllerInterface;

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
		System.out.println(controllerInterface);
		controllerInterface.uiUpdate(jsonString);
	}
	public void setControllerInterface(ControllerInterface controllerInterface) {
		this.controllerInterface = controllerInterface;
	}
}
