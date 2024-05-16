package com.dorbello.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostUnsuccessfulAdvice {

  @ExceptionHandler(PostUnsuccessfulException.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public String postUnsuccessfulHandler(PostUnsuccessfulException ex) {
    return ex.getMessage();
  }
}