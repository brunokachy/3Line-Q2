package com.verifycard.core.exception;

public class UnauthorisedException extends RuntimeException {
    public UnauthorisedException(String message){
        super(message);
    }
}
