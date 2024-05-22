import org.amalgam.lexilogicserver.model.microservices.daemonHandler.ORBDException;
import org.amalgam.lexilogicserver.model.microservices.daemonHandler.ORBDOperationCallback;
import org.amalgam.lexilogicserver.model.microservices.daemonHandler.ORBDRunner;
import org.amalgam.lexilogicserver.model.microservices.serverHandler.ORBServer;
import org.amalgam.lexilogicserver.model.microservices.serverHandler.ORBServerCallback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ORBServerTester implements ORBServerCallback, ORBDOperationCallback {
    public static ExecutorService executorDaemon = Executors.newSingleThreadExecutor();
    ExecutorService executorServer = Executors.newSingleThreadExecutor();
    boolean daemonRunning;
    Process process;
    public void runServer(){
        ORBServer server = new ORBServer(this, 2020, "localhost");
        executorServer.submit(server);
    }

    public void runDaemon(){
        ORBDRunner daemon = new ORBDRunner(this, 2020, "localhost");
        executorDaemon.submit(daemon);
    }

    public static void main(String[] args) throws InterruptedException {
        ORBServerTester program = new ORBServerTester();
//        program.runDaemon();
        Thread.sleep(3000);
        program.runServer();
        program.runDaemon();
        while(program.daemonRunning){

        }
//        program.process.destroy();
//        program.executorDaemon.shutdownNow();
//        program.executorServer.shutdownNow();
    }

    @Override
    public void notifyOrbExit() throws ORBDException {
        daemonRunning = false;
        System.out.println("Daemon closed");
    }

    @Override
    public void setProcessObject(Process process) {
        this.process = process;
    }

    @Override
    public void notifyServerShutdown() {

    }

    @Override
    public void notifyServantsBinded() {

    }
}
