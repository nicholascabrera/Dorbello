package com.dorbello.exceptions;

public class InitializationUnsuccessfulException extends RuntimeException{
    public InitializationUnsuccessfulException(String id){
        super ("Initialization of " + id + " is unsuccessful.");
    }
}
