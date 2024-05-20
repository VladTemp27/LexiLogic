import org.amalgam.lexilogicserver.model.microservices.daemonHandler.ORBDException;
import org.amalgam.lexilogicserver.model.microservices.daemonHandler.ORBDOperationCallback;
import org.amalgam.lexilogicserver.model.microservices.daemonHandler.ORBDRunner;
import org.amalgam.lexilogicserver.model.microservices.serverHandler.ORBServer;
import org.amalgam.lexilogicserver.model.microservices.serverHandler.ORBServerCallback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ORBServerTester implements ORBServerCallback, ORBDOperationCallback {
    ExecutorService executor = Executors.newCachedThreadPool();
    boolean daemonRunning;
    public void runServer(){
        ORBServer server = new ORBServer(this, 2018, "corbaServer");
        executor.submit(server);
    }

    public void runDaemon(){
        ORBDRunner daemon = new ORBDRunner(this, 2018, "corbaServer");
        executor.submit(daemon);
    }

    public static void main(String[] args) throws InterruptedException {
        ORBServerTester program = new ORBServerTester();
        program.runDaemon();
        Thread.sleep(3000);
        program.runServer();
        while(program.daemonRunning){

        }

        program.executor.shutdown();

    }

    @Override
    public void notifyOrbExit() throws ORBDException {
        daemonRunning = false;
        System.out.println("Daemon closed");
    }

    @Override
    public void notifyServerShutdown() {

    }

    @Override
    public void notifyServantsBinded() {

    }
}
