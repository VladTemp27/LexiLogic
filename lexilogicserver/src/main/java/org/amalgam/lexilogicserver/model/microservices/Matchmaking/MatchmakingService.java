package org.amalgam.lexilogicserver.model.microservices.Matchmaking;

import org.amalgam.lexilogicserver.model.microservices.NTimer;
import org.amalgam.lexilogicserver.model.microservices.NTimerCallback;
import org.amalgam.lexilogicserver.model.microservices.gamesettings.SettingsHandler;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.PlayerGameDetail;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MatchmakingService implements NTimerCallback{
    private final ConcurrentLinkedQueue<PlayerGameDetail> queue = new ConcurrentLinkedQueue<>();
    private final Semaphore queueLock = new Semaphore(1);
    private final int MATCHMAKING_TIMEOUT = SettingsHandler.getQueueTime()*1000;
    private final AtomicBoolean timerDoneValue = new AtomicBoolean(false);
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final AtomicBoolean roomValidity = new AtomicBoolean(false);
    private final AtomicBoolean timerStarted = new AtomicBoolean(false);

    public boolean isQueueEmpty(){
        return queue.isEmpty();
    }

    public void addToQueue(PlayerGameDetail playerGameDetail) {
        queue.add(playerGameDetail);
        if (queue.size() == 1 && !timerStarted.get()) {
            startTimer();
            timerStarted.set(true);
        }
    }

    public LinkedList<PlayerGameDetail> getQueue() {
        return new LinkedList<>(queue);
    }

    public void startTimer() {
        timerDoneValue.set(false);
        roomValidity.set(false);
        executorService.submit(new NTimer(MATCHMAKING_TIMEOUT / 1000, this));
    }

    @Override
    public void timerDone() {
        timerDoneValue.set(true);
        try {
            queueLock.acquire();
            System.out.println("ROOM SIZE: "+queue.size());
            System.out.println("ROOM VALID: "+(queue.size() >= 2));
            if(queue.size() >= 2){
                roomValidity.set(true);
                return;
            }
            if (queue.size() == 1) {
                queue.clear();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            timerStarted.set(false);
            queueLock.release();
        }
    }

    @Override
    public void timeIs() {}

    public LinkedList<PlayerGameDetail> checkAndMatchPlayers() throws InterruptedException {
        queueLock.acquire();
        try {
            if (queue.size() >= 2) {
                return getQueue();
            }
        } finally {
            queueLock.release();
        }
        return null;
    }

    public boolean isTimerDone() {
        return timerDoneValue.get();
    }

    public boolean isRoomValid(){
//        System.out.println("Returning room validity: "+roomValidity.get());
        return roomValidity.get();
    }

    public boolean clearQueue(){
        if(timerDoneValue.get()){
            queue.clear();
            return queue.isEmpty();
        }
        return false;
    }
}