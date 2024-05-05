package org.amalgam.lexilogicserver.model.microservices.WordBox;

import org.amalgam.lexilogicserver.model.microservices.WordBox.Exceptions.ReadFailure;

import java.security.InvalidParameterException;
import java.util.LinkedList;

public class Generator {
    private LinkedList<String> wordList;
    private char[][] wordbox;

    public Generator(Reader wordReader, boolean isCSV) throws InvalidParameterException {
        try {
            wordList = wordReader.retrieveListOfWords(isCSV);
        }catch(ReadFailure e){
            throw new InvalidParameterException(e.getMessage());
        }
    }

    //TODO: to populate logic for actual logic that generates a matrix of letters
    public char[][] generateWordBox(){
        return wordbox;
    }



}
