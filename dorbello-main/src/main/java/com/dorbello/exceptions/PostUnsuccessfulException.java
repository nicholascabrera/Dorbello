package com.dorbello.exceptions;

public class PostUnsuccessfulException extends RuntimeException{
    public PostUnsuccessfulException(String id){
        super("Post for " + id + " was unsuccessful.");
    }
}
