package org.amalgam.lexilogicserver.model.microservices.daemonHandler;

//Class that will use the ORBRunner should implement this interface
public interface ORBOperationCallback {
    public void notifyOrbExit()throws ORBException;
}
