package org.amalgam.lexilogicserver.model.microservices.daemonHandler;

import java.util.concurrent.*;

//Sample Implementation of the ORBRunner
public class ORBDExample implements ORBOperationCallback{
    private Future<Integer> exitCode;
    private int exit;

    public static void main(String[] args) {
        ORBDExample program = new ORBDExample();
        program.run();
    }

    public void run(){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        ORBRunner orbRunner = new ORBRunner(this, 2018, "localhost");
        exitCode = executor.submit(orbRunner);
    }
    @Override
    public void notifyOrbExit() throws ORBException {
        try {
            exit = exitCode.get();
            throw new ORBException("ORB has exited with exit code: " + exitCode.get());
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getExit() {
        return exit;
    }
}
