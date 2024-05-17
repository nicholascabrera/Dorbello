package com.dorbello.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PutUnsuccessfulAdvice {

  @ExceptionHandler(PutUnsuccessfulException.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public String initializationUnsuccessfulHandler(PutUnsuccessfulException ex) {
    return ex.getMessage();
  }
}