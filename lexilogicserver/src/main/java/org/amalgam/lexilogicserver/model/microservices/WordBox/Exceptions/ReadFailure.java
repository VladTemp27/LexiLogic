package org.amalgam.lexilogicserver.model.microservices.WordBox.Exceptions;

public class ReadFailure extends Exception{
    public ReadFailure(String message){
        super(message);
    }
}
