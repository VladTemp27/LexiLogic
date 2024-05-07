package org.amalgam.lexilogicserver.model.microservices.wordbox;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Objects;

public class WordBox {
    private Generator generator;
    private char[][] wordMatrix;
    private LinkedHashMap<Character, Integer> boxChars = new LinkedHashMap<>();

    /**
     * Constructor of WordBox
     * @param generator     Needs an object of generator where it will use it to generate the wordbox as well as retrieving
     *                      the word list
     */
    public WordBox(Generator generator){
        this.wordMatrix = generator.generateWordBox();
        populateBoxChars(this.wordMatrix);
        this.generator = generator;
    }

    public char[][] getWordMatrix() {
        return wordMatrix;
    }

    public void setWordMatrix(char[][] wordMatrix) {
        this.wordMatrix = wordMatrix;
    }

    /**
     * Method to be called to verify a word that has been submitted related to an object of WordBox
     * @param word      String of word to be verified
     * @return          Returns an int value, which would be the points from the word
     */
    public int verifyWord(String word){
        LinkedHashMap<Character, Integer> charactersInWord = getCharsFromWord(word);
        for(Character character : charactersInWord.keySet()){
            if(!(charactersInWord.get(character)<=boxChars.get(character))){
                return 0;
            }
        }
        // check list of words if it contains given word
        LinkedList<String> wordList = generator.getWordList();
        if(wordList.contains(word)) {
            return word.length();
        }
        return 0;
    }

    //This method gets the characters in a word as well as their number of occurrences and stores them in a
    //Hashmap
    private LinkedHashMap<Character, Integer> getCharsFromWord(String word){
        LinkedHashMap<Character, Integer> occurrencesFromWord = new LinkedHashMap<>();
        for(int index=0; index < word.length(); index++){
            char currentCharacter = word.charAt(index);
            addCharOccurrence(currentCharacter, occurrencesFromWord);
        }
        return occurrencesFromWord;
    }

    // This method will populate the boxChars which holds the characters in the wordbox as well as their number of
    // occurrences
    private void populateBoxChars(char[][] wordMatrix){
        for(int row=0; row<wordMatrix.length; row++){
            for(int col=0;col<wordMatrix[row].length;col++){

                char currentCharacter = wordMatrix[row][col];
                addCharOccurrence(currentCharacter, this.boxChars);
            }
        }
    }

    //This method adds a character as a Key to a Hashmap with the value being the number of occurrences this character
    // has
    private void addCharOccurrence(char character, LinkedHashMap<Character, Integer> hashMapOfOccurences){
        Integer count = null;
        count = hashMapOfOccurences.get(character);
        if(count != null){
            count++;
            hashMapOfOccurences.replace(character, count);
        }else{
            hashMapOfOccurences.put(character, 1);
        }
    }

}
