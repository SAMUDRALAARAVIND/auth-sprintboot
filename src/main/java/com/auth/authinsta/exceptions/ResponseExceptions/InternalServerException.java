package com.auth.authinsta.exceptions.ResponseExceptions;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message){
        super(message);
    }
}
