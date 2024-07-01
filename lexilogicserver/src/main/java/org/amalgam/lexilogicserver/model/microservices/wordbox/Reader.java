package org.amalgam.lexilogicserver.model.microservices.wordbox;

import org.amalgam.lexilogicserver.model.microservices.wordbox.Exceptions.ReadFailure;

import java.io.*;
import java.util.LinkedList;

public class Reader {
    private File fileOfWordList;
    private FileReader fileReader;

    /**
     * Default constructor to be used for generating an object of Reader
     * @param wordListFile              Object of File where the word list resides
     * @throws FileNotFoundException    throws this exception if the file does not exist or if the Reader has a problem
     *                                     finding the file
     */
    public Reader(File wordListFile) throws FileNotFoundException{
        this.fileOfWordList = wordListFile;
        fileReader = new FileReader(fileOfWordList);

    }

    /**
     * Secondary constructor that overloads the default constructor shall the need arise where a path is only available
     * @param pathToFile                    String of path to the word list file
     * @throws FileNotFoundException        throws if Reader is unable to find the file
     */
    public Reader(String pathToFile) throws FileNotFoundException{
        fileOfWordList = new File(pathToFile);
        fileReader = new FileReader(fileOfWordList);
    }

    /**
     * Method to be used by Generator
     * @param isCSV         boolean if file is CSV or not
     * @return              returns a LinkedList of String that will hold the words from the file
     * @throws ReadFailure  Throws ReadFailure if IOException occurs
     */
    public LinkedList<String> retrieveListOfWords(boolean isCSV) throws ReadFailure {
        BufferedReader bufferedreader = new BufferedReader(fileReader);

        try{
            if(isCSV){
                return processCSV(bufferedreader);
            }else{
                return processNonCSV(bufferedreader);
            }
        }catch (IOException e){
            throw new ReadFailure(e.getMessage());
        }
    }

    //Method that will process the file if the file is a CSV, this will return a LinkedList of String which would
    //  be the words
    private LinkedList<String> processNonCSV(BufferedReader bReader) throws IOException {
        LinkedList<String> retrieved = new LinkedList<>();
        String word;

        while((word = bReader.readLine()) != null){
            if (!(retrieved.contains(word))){
                retrieved.add(word);
            }
        }

        return retrieved;
    }

    //Method that will process the file if the file is not a CSV, this will return a LinkedList of String which would
    //  be the words
    private LinkedList<String> processCSV(BufferedReader bReader) throws IOException {
        String data = "";
        String buffer;
        LinkedList<String> retrieved = new LinkedList<>();

        while((buffer = bReader.readLine())!= null){
            char lastChar = buffer.charAt(buffer.length()-1);

            if(lastChar != ','){
                buffer+=",";
            }
            data+=buffer;
        }

        String[] segregated = data.split(",");

        for(String word: segregated){

            if(!(retrieved.contains(word))) {
                retrieved.add(word);
            }
        }

        retrieved.remove("");
        return retrieved;
    }
}
