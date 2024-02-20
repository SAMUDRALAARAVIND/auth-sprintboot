package com.auth.authinsta.exceptions.ResponseExceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
