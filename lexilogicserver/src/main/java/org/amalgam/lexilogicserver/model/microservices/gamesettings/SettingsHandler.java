package org.amalgam.lexilogicserver.model.microservices.gamesettings;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class SettingsHandler {
    private static final String filePath = "lexilogicserver/src/main/java/org/amalgam/lexilogicserver/model/microservices/gamesettings/settings.json";
    private static synchronized JsonObject getObjectFromFile(String path){
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

    public static int getQueueTime(){
        JsonObject rootObject = getObjectFromFile(filePath);
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

    public static void setQueueTime(int newQueueTime) {

        JsonObject rootObject = getObjectFromFile(filePath);

        JsonArray elementsArray = rootObject.getAsJsonArray("settings");

        JsonObject settingsObject = elementsArray.get(0).getAsJsonObject();

        settingsObject.addProperty("queueTime", String.valueOf(newQueueTime));

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(rootObject.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error writing to settings file", e);
        }
    }
    public static int getGameTime(){
        JsonObject rootObject = getObjectFromFile(filePath);
        JsonArray elementsArray = rootObject.getAsJsonArray("settings");
        for(JsonElement element : elementsArray){
            JsonObject currentObject = element.getAsJsonObject();
            String gameTime;
            if((gameTime = currentObject.get("gameTime").getAsString()) != null){
                return Integer.parseInt(gameTime);
            }
        }
        return 0;
    }

    public static void setGameTime(int gameTime){
        JsonObject rootObject = getObjectFromFile(filePath);

        JsonArray elementsArray = rootObject.getAsJsonArray("settings");

        JsonObject settingsObject = elementsArray.get(0).getAsJsonObject();

        settingsObject.addProperty("gameTime", String.valueOf(gameTime));

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(rootObject.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error writing to settings file", e);
        }
    }

    public static void main(String[] args) {
        int time = getQueueTime();
        System.out.println(time);
    }
}
