import org.amalgam.lexilogicserver.model.microservices.daemonHandler.ORBDException;
import org.amalgam.lexilogicserver.model.microservices.daemonHandler.ORBDOperationCallback;
import org.amalgam.lexilogicserver.model.microservices.daemonHandler.ORBDRunner;
import org.amalgam.lexilogicserver.model.microservices.serverHandler.ORBServer;
import org.amalgam.lexilogicserver.model.microservices.serverHandler.ORBServerCallback;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ORBTester implements ORBServerCallback, ORBDOperationCallback {
	static ExecutorService executorService = Executors.newCachedThreadPool();
	static boolean daemonRunning = false;

	public static void main(String[] args) throws InterruptedException {

	}

	public void runDaemon(){
		executorService.submit(new ORBDRunner(this, 2018, "localhost"));
	}

	public void runServer() {
		executorService.submit(new ORBServer(this, 2018,"localhost"));
		System.out.println(executorService.isTerminated());
	}

	@Override
	public void notifyServerShutdown() {
		System.out.println("Server has closed");
	}

	@Override
	public void notifyServantsBinded() {
		System.out.println("Servants bound");
	}

	@Override
	public void notifyOrbExit() throws ORBDException {
		System.out.println("Daemon has closed");
		daemonRunning=false;
	}
}
