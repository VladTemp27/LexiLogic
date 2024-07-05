package org.amalgam;

import org.amalgam.ControllerException.InvalidRequestException;
import org.amalgam.UIControllers.PlayerCallbackPOA;

public class PlayerCallbackImpl extends PlayerCallbackPOA{
	private String username;
	private UpdateDispatcher updateDispatcher;

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
		System.out.println(username + " " + jsonString);
		updateDispatcher.update(jsonString);
	}
	public void setControllerInterface(UpdateDispatcher updateDispatcher) {
		this.updateDispatcher = updateDispatcher;
	}
}
