package org.amalgam.lexilogicserver.model.microservices.wordbox;

import org.amalgam.lexilogicserver.model.microservices.wordbox.Exceptions.ReadFailure;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.Random;

public class Generator {
    private LinkedList<String> wordList;
    private char[][] charMatrix;
    int rowSize, colSize;
    private int vowelCount;
    private int consonantCount;

    /**
     * Default constructor of generator, this object will be used when generating a wordbox
     * @param dictionary    LinkedList of String that will be dictionary word list for the validator
     * @param rowSize       Row size of the word box to be generated
     * @param colSize       Col size of the word box to be generated
     * @throws InvalidParameterException    Throws invalid param if file given within the Reader object is invalid
     */
    public Generator(LinkedList<String> dictionary, int rowSize, int colSize) throws InvalidParameterException {
        this.wordList = dictionary;
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
        Random random = new Random();
        char[] characterList;
        char randomChar;
        if(vowelCount == 7){
            characterList = getConsonantOrVowelList(1);
            int randomIndex = random.nextInt(characterList.length);
            randomChar = characterList[randomIndex];
            return randomChar;
        }

        if(consonantCount == 13){
            characterList = getConsonantOrVowelList(0);
            int randomIndex = random.nextInt(characterList.length);
            randomChar = characterList[randomIndex];
            return randomChar;
        }
        int randomCharType = random.nextInt(2);

        characterList = getConsonantOrVowelList(randomCharType);
        int randomIndex = random.nextInt(characterList.length);
        randomChar = characterList[randomIndex];
        return randomChar;
    }

    private char[] getConsonantOrVowelList(int charType){
        char[] vowels = {'a','e','i','o','u'};
        char[] consonants = {
                'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'
        };
        switch(charType){
            case 0:
                vowelCount++;
                return vowels;
            case 1:
                consonantCount++;
                return consonants;
        }
        return null;

    }

}
