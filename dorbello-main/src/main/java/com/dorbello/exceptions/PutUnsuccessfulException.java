package com.dorbello.exceptions;

public class PutUnsuccessfulException extends RuntimeException{
    public PutUnsuccessfulException(String id){
        super ("Update of " + id + " is unsuccessful.");
    }
}
