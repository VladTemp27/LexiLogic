package org.amalgam.lexilogicserver.model.utilities.referenceobjects;

import org.amalgam.lexilogicserver.model.microservices.wordbox.WordBox;

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

    public PlayerGameDetail() {
    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
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

    public LinkedList<String> getDupedWords(){return dupedWords;}

    public boolean listOfWordsContains(String word){
        return words.contains(word);
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
