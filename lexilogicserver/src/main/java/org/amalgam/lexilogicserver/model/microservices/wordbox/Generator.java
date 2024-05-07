package org.amalgam.lexilogicserver.model.microservices.wordbox;

import org.amalgam.lexilogicserver.model.microservices.wordbox.Exceptions.ReadFailure;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.Random;

public class Generator {
    private LinkedList<String> wordList;
    private char[][] charMatrix;
    int rowSize, colSize;

    /**
     * Default constructor of generator, this object will be used when generating a wordbox
     * @param wordReader    Object of Reader that will read the word list file
     * @param isCSV         if the file where the words are stored is csv or not
     * @param rowSize       Row size of the word box to be generated
     * @param colSize       Col size of the word box to be generated
     * @throws InvalidParameterException    Throws invalid param if file given within the Reader object is invalid
     */
    public Generator(Reader wordReader, boolean isCSV, int rowSize, int colSize) throws InvalidParameterException {
        try {
            wordList = wordReader.retrieveListOfWords(isCSV);
        }catch(ReadFailure e){
            throw new InvalidParameterException(e.getMessage());
        }
        this.rowSize = rowSize;
        this.colSize = colSize;
    }

    /**
     * Method that must be called to generate a matrix of characters
     * @return  2 Dimensional array of characters
     */
    public char[][] generateWordBox(){
        charMatrix = new char[rowSize][colSize];
        for(int row = 0; row < rowSize; row++){
            for(int col = 0; col < colSize; col++){
                charMatrix[row][col] = randomLowerCaseCharacter();
            }
        }

        return charMatrix;
    }

    public LinkedList<String> getWordList() {
        return wordList;
    }

    //Method for generating a random lower case character a - z
    private char randomLowerCaseCharacter(){
        int leftLimit = 97; // 'a' character
        int rightLimit = 122; // 'z' character
        Random random = new Random();

        char c = (char) (random.nextInt(rightLimit - leftLimit + 1) + leftLimit);
        return c;
    }

}
