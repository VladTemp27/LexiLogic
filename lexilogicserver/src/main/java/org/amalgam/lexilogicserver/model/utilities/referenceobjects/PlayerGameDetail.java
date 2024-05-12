package org.amalgam.lexilogicserver.model.utilities.referenceobjects;

import org.amalgam.lexilogicserver.model.microservices.wordbox.WordBox;

import java.util.LinkedList;

public class PlayerGameDetail {
    private String username;
    private int points = 0;
    private LinkedList<String> words=new LinkedList<>();;
    private LinkedList<String> dupedWords=new LinkedList<>();

    public PlayerGameDetail(String username) {
        this.username = username;
        words = new LinkedList<>();
        dupedWords = new LinkedList<>();
    }

    public PlayerGameDetail() {
    }

    public void submitWord(String word, WordBox wordBox){
        if(words.contains(word)){
            dupedWords.add(word);
        }
        if(wordBox.verifyWord(word)!= 0){
            words.add(word);
            calculatePoints();
        }
    }

    private void calculatePoints(){
        for(String word : words){
            points += word.length();
        }

        for(String duped: dupedWords){
            points -= duped.length();
        }
    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }

    public LinkedList<String> getWords() {
        return words;
    }
}
