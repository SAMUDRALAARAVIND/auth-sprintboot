package com.auth.authinsta.exceptions.ResponseExceptions;

public class UnAuthorizedException extends  RuntimeException{
    public UnAuthorizedException(String message) {
        super(message);
    }
}
