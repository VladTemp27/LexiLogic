package org.amalgam.lexilogicserver.model.utilities.referenceobjects;
import java.util.LinkedList;

public class PlayerGameDetail {
    private String username;
    private int points = 0;
    private LinkedList<String> words=new LinkedList<>();;
    private LinkedList<String> dupedWords=new LinkedList<>();
    private boolean ready = false;

    public PlayerGameDetail(String username) {
        this.username = username;
        words = new LinkedList<>();
        dupedWords = new LinkedList<>();
    }

    public LinkedList<String> getDupedWords() {
        return dupedWords;
    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        int calculatedPoints = points;
        for(String word:dupedWords){
            if (listOfWordsContains(word)){
                calculatedPoints-= word.length();
            }
        }
        return calculatedPoints;
    }

    public void setPoints(int points){this.points = points;}

    public LinkedList<String> getWords() {
        return words;
    }

    public void addWord(String word){
        words.add(word);
    }

    public void addDupedWord(String word){
        dupedWords.add(word);
    }

    public boolean listOfWordsContains(String word){
        return words.contains(word);
    }

    public boolean listOfDupesContains(String word) { return dupedWords.contains(word);}

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
