package org.amalgam.lexilogicserver.model.microservices.WordBox;

import org.amalgam.lexilogicserver.model.microservices.WordBox.Exceptions.ReadFailure;

import java.io.*;
import java.util.LinkedList;

public class Reader {
    private File fileOfWordList;
    private String filePath;
    private FileReader fileReader;

    public Reader(File wordList) throws FileNotFoundException{
        this.fileOfWordList = wordList;
        fileReader = new FileReader(fileOfWordList);

    }

    public Reader(String pathToFile) throws FileNotFoundException{
        fileOfWordList = new File(pathToFile);
        fileReader = new FileReader(fileOfWordList);
    }

    protected LinkedList<String> retrieveListOfWords(boolean isCSV) throws ReadFailure {
        LinkedList<String> retrievedWords = new LinkedList<>();
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

    private LinkedList<String> processNonCSV(BufferedReader bReader) throws IOException {
        LinkedList<String> retrieved = new LinkedList<>();
        String word;

        if((word = bReader.readLine()) != null){
            if (!(retrieved.contains(word))){
                retrieved.add(word);
            }
        }

        return retrieved;
    }

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
