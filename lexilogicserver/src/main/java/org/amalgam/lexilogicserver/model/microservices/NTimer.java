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
        try {
            Thread.sleep(1000*secondsDuration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        callback.timerDone();
    }
}
