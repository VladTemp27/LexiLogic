package org.amalgam.lexilogicserver.model.helpers;

import org.amalgam.UIControllers.PlayerCallback;
import org.amalgam.lexilogicserver.model.microservices.Matchmaking.MatchmakingService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class MatchMakeCleaner implements Runnable{
    private Semaphore matchmakingLock;
    private MatchmakingService matchmakingService;

    private AtomicBoolean roomCreationAllowed;
    private AtomicBoolean roomCreated;

    private ConcurrentHashMap<String, PlayerCallback> playerCallbackMap;

    public MatchMakeCleaner(Semaphore matchmakingLock, MatchmakingService mmService, AtomicBoolean roomCreationAllowed,
                            AtomicBoolean roomCreated, ConcurrentHashMap<String, PlayerCallback> playerCallbackMap){
       this.matchmakingLock = matchmakingLock;
       this.matchmakingService = mmService;
       this.roomCreationAllowed = roomCreationAllowed;
       this.roomCreated = roomCreated;
       this.playerCallbackMap = playerCallbackMap;
    }

    @Override
    public void run() {
        try{
            matchmakingLock.acquire();
            while(true){
                if(matchmakingService.isTimerDone()) break;
            }

            while(true){
                if(matchmakingService.responsesDone()){
                    System.out.println("Responses have been sent");
                    System.out.println("Resetting MM for next users");
                    this.matchmakingService.clearQueue();
                    this.playerCallbackMap.clear();
                    this.roomCreationAllowed.set(true);
                    this.roomCreated.set(false);
                }
                break;
            }
            matchmakingLock.release();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
