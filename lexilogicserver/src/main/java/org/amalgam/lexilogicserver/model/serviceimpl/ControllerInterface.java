package org.amalgam.lexilogicserver.model.serviceimpl;

import org.amalgam.Service.PlayerExceptions.InvalidRequest;
import org.amalgam.Service.PlayerExceptions.UserExistenceException;
import org.amalgam.Service.PlayerServiceModule.ControllerInterfacePOA;

public class ControllerInterface extends ControllerInterfacePOA {
    @Override
    public void setObjectsUser(String objects) throws InvalidRequest, UserExistenceException {

    }

    @Override
    public void fetchAndUpdate() throws InvalidRequest {

    }
}
