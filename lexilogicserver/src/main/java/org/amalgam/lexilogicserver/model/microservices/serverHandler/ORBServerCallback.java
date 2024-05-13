package org.amalgam.lexilogicserver.model.microservices.serverHandler;

public interface ORBServerCallback {
    void notifyServerShutdown();
    void notifyServantsBinded();
}
