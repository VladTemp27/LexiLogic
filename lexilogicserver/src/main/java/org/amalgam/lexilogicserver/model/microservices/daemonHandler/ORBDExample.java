package org.amalgam.lexilogicserver.model.microservices.daemonHandler;

import java.util.concurrent.*;

//Sample Implementation of the ORBRunner
public class ORBDExample implements ORBDOperationCallback {
    private Future<Integer> exitCode;
    private int exit;

    public static void main(String[] args) {
        ORBDExample program = new ORBDExample();
        program.run();
    }

    public void run(){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        ORBDRunner orbdRunner = new ORBDRunner(this, 2018, "localhost");
        exitCode = executor.submit(orbdRunner);
    }
    @Override
    public void notifyOrbExit() throws ORBDException {
        try {
            exit = exitCode.get();
            throw new ORBDException("ORB has exited with exit code: " + exitCode.get());
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getExit() {
        return exit;
    }
}
