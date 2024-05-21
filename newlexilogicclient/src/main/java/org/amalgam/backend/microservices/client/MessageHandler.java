package org.amalgam.backend.microservices.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MessageHandler {
    public String process (String message){
        JsonObject jsonObject = new Gson().fromJson(message, JsonObject.class);
        String state = jsonObject.get("state").getAsString();

        switch (state){
            case "staging":
                int roundDurationSeconds = jsonObject.get("countdown").getAsInt();
                startCountdownTimer(roundDurationSeconds);
                return jsonObject.get("game_room").getAsString();

            case "game_started":
                return jsonObject.get("game_room").getAsString();

            case "game_done":
                return jsonObject.get("winner").getAsString();
        }
        return null;
    }



     private void startCountdownTimer(int durationSeconds) {
        new Thread(() -> {
            try {
                System.out.println("Countdown started for " + durationSeconds + " seconds");
                Thread.sleep(durationSeconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
