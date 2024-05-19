package org.amalgam.lexilogicserver.model.microservices;

public class NTimer implements Runnable {
    private int secondsDuration;
    private NTimerCallback callback;

    public NTimer(int secondsDuration, NTimerCallback callback){
        this.secondsDuration = secondsDuration;
        this.callback = callback;
    }

    @Override
    public void run() {
        //Timer for  secondsDuration
        callback.timerDone();
    }
}
