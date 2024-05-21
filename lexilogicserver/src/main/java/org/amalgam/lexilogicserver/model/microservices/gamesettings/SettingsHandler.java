package org.amalgam.lexilogicserver.model.microservices.gamesettings;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class SettingsHandler {

    private synchronized JsonObject getObjectFromFile(String path){
        File settingsFile = new File(path);
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(settingsFile))) {
            JsonElement jsonElement = JsonParser.parseReader(bufferedReader);
            return jsonElement.getAsJsonObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getQueueTime(){
        JsonObject rootObject = getObjectFromFile("settings.json");
        JsonArray elementsArray = rootObject.getAsJsonArray("settings");
        for(JsonElement element : elementsArray){
            JsonObject currentObject = element.getAsJsonObject();
            String queueTime;
            if((queueTime = currentObject.get("queueTime").getAsString()) != null){
                return Integer.parseInt(queueTime);
            }
        }
        return 0;
    }
}
