package org.amalgam.lexilogicserver.model.microservices.Matchmaking;

import org.amalgam.lexilogicserver.model.microservices.NTimer;
import org.amalgam.lexilogicserver.model.microservices.NTimerCallback;
import org.amalgam.lexilogicserver.model.utilities.referenceobjects.PlayerGameDetail;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MatchmakingService implements NTimerCallback{
    private final ConcurrentLinkedQueue<PlayerGameDetail> queue = new ConcurrentLinkedQueue<>();
    private final Semaphore queueLock = new Semaphore(1);
    private Thread timerThread;
    private final int MATCHMAKING_TIMEOUT = 10000;
    private final AtomicBoolean timerDone = new AtomicBoolean(false);

    public void addToQueue(PlayerGameDetail playerGameDetail) {
        queue.add(playerGameDetail);
        if (queue.size() == 1) {
            startTimer();
        }
    }

    public void startTimer() {
        timerDone.set(false);
        NTimer timer = new NTimer(MATCHMAKING_TIMEOUT / 1000, this);
        timerThread = new Thread(timer);
        timerThread.start();
    }

    @Override
    public void timerDone() {
        timerDone.set(true);
        try {
            queueLock.acquire();
            if (queue.size() == 1) {
                queue.clear();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            queueLock.release();
        }
    }

    @Override
    public void timeIs() {
    }

    public LinkedList<PlayerGameDetail> checkAndMatchPlayers() throws InterruptedException {
        queueLock.acquire();
        try {
            if (queue.size() >= 2) {
                LinkedList<PlayerGameDetail> players = new LinkedList<>();
                for (int i = 0; i < 2; i++) {
                    players.add(queue.poll());
                }
                return players;
            }
        } finally {
            queueLock.release();
        }
        return null;
    }

    public boolean isTimerDone() {
        return timerDone.get();
    }
}