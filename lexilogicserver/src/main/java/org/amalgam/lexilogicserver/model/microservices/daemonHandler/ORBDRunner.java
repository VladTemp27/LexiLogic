package org.amalgam.lexilogicserver.model.microservices.daemonHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

//Use this object when running the daemon
//Submit this to an ExecutorService of sort
public class ORBDRunner implements Callable<Integer> {
    private ORBDOperationCallback callback;
    private int port;
    private String hostname;

    /**
     * Default constructor
     * @param callback     Object of ORBOperationCallback this is necessary and will notify the invoker if the daemon has exited
     * @param port         Port where to run the daemon(Integer)
     * @param hostname     Hostname where to run the daemon(String)
     */
    public ORBDRunner(ORBDOperationCallback callback, int port, String hostname) {
        this.callback = callback;
        this.port = port;
        this.hostname = hostname;
    }

    @Override
    public Integer call() throws Exception {
        // Create ProcessBuilder for orbd command
        ProcessBuilder builder = new ProcessBuilder("orbd","-J-verbose",
                "-J-Djava.net.preferIPv4Stack=true",
                "-J-Djava.net.preferIPv6Addresses=false", "-ORBInitialPort", String.valueOf(port), "-ORBInitialHost", hostname);

        // Redirect error stream to output
        builder.redirectErrorStream(true);

        // Start the process
        Process process = builder.start();
        callback.setProcessObject(process);

        // Read output
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        // Wait for the command to finish
        int exitCode = process.waitFor();
        callback.notifyOrbExit();
        return exitCode;
    }
}
