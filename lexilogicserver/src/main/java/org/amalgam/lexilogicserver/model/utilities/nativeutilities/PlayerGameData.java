package org.amalgam.lexilogicserver.model.utilities.nativeutilities;

import org.amalgam.Service.GameExceptions.DuplicateWordException;

import java.util.LinkedList;

public class PlayerGameData {
    private String name;
    private LinkedList<String> words;

    public PlayerGameData() {
        this.name = null;
        words = new LinkedList<>();
    }

    public PlayerGameData(String name, int pts, LinkedList<String> words) {
        this.name = name;
        this.words = words;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<String> getWords() {
        return words;
    }

    public void setWords(LinkedList<String> words) {
        this.words = words;
    }

    public void addWord(String word) throws DuplicateWordException {
        if(!words.contains(word)){
            words.add(word);
        }else{
            throw new DuplicateWordException("User has already submitted this word");
        }
    }


}
