package org.amalgam.lexilogicserver.model.microservices.wordbox.Exceptions;

public class ReadFailure extends Exception{
    public ReadFailure(String message){
        super(message);
    }
}
